/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.proyectoconsulta;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import javax.swing.JOptionPane;
/**
 *
 * @author joels
 */
public class Consulta extends javax.swing.JFrame {
    private ArrayList<String> preguntas = new ArrayList<>();
    private ArrayList<String> respuestas = new ArrayList<>();
    private int indicePregunta = 0;
    
     // Listas de síntomas por enfermedad
    private ArrayList<String> sintomasIntolerancia = new ArrayList<>();
    private ArrayList<String> sintomasCancerEstomago = new ArrayList<>();
    private ArrayList<String> sintomasCancerColon = new ArrayList<>();
    
    public Consulta() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
         // Inicializar todos los síntomas (preguntas)
        inicializarSintomas();
        inicializarPreguntas();
 // Inicializamos las preguntas en el ArrayList
 
        // Inicializar las respuestas con valores vacíos
    // Inicializar respuestas con valores vacíos
        for (int i = 0; i < preguntas.size(); i++) {
            respuestas.add("");
        }

        // Mostrar la primera pregunta
        mostrarPregunta();

        // Agregar listeners a los botones
        jButton1.addActionListener(e -> responder("Sí"));
        jButton2.addActionListener(e -> responder("No"));
    }
    
    private void inicializarSintomas() {
        // Síntomas de intolerancia a la lactosa
        sintomasIntolerancia.add("¿Has sentido distensión?");
        sintomasIntolerancia.add("¿Padeces diarrea?");
        sintomasIntolerancia.add("¿Has tenido náuseas o vómitos?");
        sintomasIntolerancia.add("¿Has sentido dolor en el abdomen?");
        sintomasIntolerancia.add("¿Tienes ruidos estomacales?");
        sintomasIntolerancia.add("¿Has sentido hinchazón?");
        sintomasIntolerancia.add("¿Has tenido vómitos?");

        // Síntomas de cáncer de estómago
        sintomasCancerEstomago.add("¿Has tenido náuseas o vómitos?");
        sintomasCancerEstomago.add("¿Sientes acidez?");
        sintomasCancerEstomago.add("¿Has tenido problemas para tragar?");
        sintomasCancerEstomago.add("¿Has sentido dolor en el abdomen?");
        sintomasCancerEstomago.add("¿Has tenido sangrado últimamente?");
        sintomasCancerEstomago.add("¿Has perdido peso?");
        sintomasCancerEstomago.add("¿Tienes fatiga excesiva?");
        sintomasCancerEstomago.add("¿Tienes piel u ojos amarillos?");
        sintomasCancerEstomago.add("¿Has perdido el apetito?");
        sintomasCancerEstomago.add("¿Sientes hinchazón constante?");

        // Síntomas de cáncer de colon
        sintomasCancerColon.add("¿Te has sentido estreñido?");
        sintomasCancerColon.add("¿Padeces diarrea?");
        sintomasCancerColon.add("¿Has sentido dolor en el abdomen?");
        sintomasCancerColon.add("¿Has tenido sangrado últimamente?");
        sintomasCancerColon.add("¿Sientes calambres abdominales?");
        sintomasCancerColon.add("¿Sientes debilidad o fatiga?");
        sintomasCancerColon.add("¿Has tenido una sensación de querer ir al baño sin alivio?");
    }
    
       private void inicializarPreguntas() {
        // Agregar todos los síntomas (sin duplicar preguntas)
        preguntas.addAll(sintomasIntolerancia);
        preguntas.addAll(sintomasCancerEstomago);
        preguntas.addAll(sintomasCancerColon);

        // Eliminar duplicados (ya que algunos síntomas son comunes entre enfermedades)
        preguntas = new ArrayList<>(new java.util.LinkedHashSet<>(preguntas));
    }
         private void mostrarPregunta() {
    // Mostrar la pregunta actual en el jLabel2
    if (indicePregunta < preguntas.size()) {
        jLabel2.setText(preguntas.get(indicePregunta));
    } else {
        // Verificar si todas las respuestas son "Sí"
        boolean todasSi = respuestas.stream().allMatch(respuesta -> "Sí".equalsIgnoreCase(respuesta));
        
        if (todasSi) {
            JOptionPane.showMessageDialog(
                this,
                "Has respondido 'Sí' a todas las preguntas. Te recomendamos volver a realizar el test.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE
            );
            // Reiniciar el test
            reiniciarTest();
        } else {
            // Si no todas las respuestas son "Sí", mostrar diagnóstico
            StringBuilder resumen = new StringBuilder("Respuestas:\n");
            for (int i = 0; i < preguntas.size(); i++) {
                resumen.append(preguntas.get(i)).append(" Respuesta: ").append(respuestas.get(i)).append("\n");
            }

            // Calcular porcentajes por enfermedad
            double porcentajeIntolerancia = calcularPorcentaje(sintomasIntolerancia);
            double porcentajeCancerEstomago = calcularPorcentaje(sintomasCancerEstomago);
            double porcentajeCancerColon = calcularPorcentaje(sintomasCancerColon);

            // Mostrar porcentajes en el resumen
            resumen.append("\nPorcentajes probables:");
            resumen.append("\n- Intolerancia a la lactosa: ").append(String.format("%.2f", porcentajeIntolerancia)).append("%");
            resumen.append("\n- Cáncer de estómago: ").append(String.format("%.2f", porcentajeCancerEstomago)).append("%");
            resumen.append("\n- Cáncer de colon: ").append(String.format("%.2f", porcentajeCancerColon)).append("%");

            // Mostrar resumen al usuario
            JOptionPane.showMessageDialog(this, resumen.toString(), "Resumen", JOptionPane.INFORMATION_MESSAGE);

            new Diagnostico().setVisible(true);
            this.dispose(); // Cerrar el JFrame actual
        }
    }
}

private void reiniciarTest() {
    // Reiniciar las respuestas y el índice
    respuestas.replaceAll(respuesta -> ""); // Reinicia todas las respuestas a cadenas vacías
    indicePregunta = 0; // Reinicia el índice de las preguntas
    mostrarPregunta(); // Muestra la primera pregunta de nuevo
}


    private double calcularPorcentaje(ArrayList<String> sintomasEspecificos) {
        int coincidencias = 0;
        for (int i = 0; i < preguntas.size(); i++) {
            if (sintomasEspecificos.contains(preguntas.get(i)) && "Sí".equalsIgnoreCase(respuestas.get(i))) {
                coincidencias++;
            }
        }
        return (coincidencias * 100.0) / sintomasEspecificos.size();
    }
      
  private void responder(String respuesta) {
        // Guardar la respuesta correspondiente a la pregunta actual
        respuestas.set(indicePregunta, respuesta);

        // Incrementar el índice para pasar a la siguiente pregunta
        indicePregunta++;

        // Mostrar la siguiente pregunta
        mostrarPregunta();
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 153, 255));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("jLabel2");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 610, -1));

        jButton1.setBackground(new java.awt.Color(102, 255, 102));
        jButton1.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jButton1.setText("SI");
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 210, 80, 50));

        jButton2.setBackground(new java.awt.Color(255, 102, 102));
        jButton2.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jButton2.setText("NO");
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 80, 50));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\joels\\Downloads\\02_Jun_2012_14_26_08_house1.jpg")); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 640, 350));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel5.setIcon(new javax.swing.ImageIcon("C:\\Users\\joels\\Downloads\\pared.jpg")); // NOI18N
        jLabel5.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 230, 150));

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel6.setIcon(new javax.swing.ImageIcon("C:\\Users\\joels\\Downloads\\pared.jpg")); // NOI18N
        jLabel6.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 0, 230, 150));

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel7.setIcon(new javax.swing.ImageIcon("C:\\Users\\joels\\Downloads\\pared.jpg")); // NOI18N
        jLabel7.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, 230, 150));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Consulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Consulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Consulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Consulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Consulta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    // End of variables declaration//GEN-END:variables
}
