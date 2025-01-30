
package com.mycompany.proyectoconsulta;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import javax.swing.JOptionPane;
/**
 *
 * @author pcita
 */
public class Consulta extends javax.swing.JFrame {
    private ArrayList<String> preguntas = new ArrayList<>();
    private ArrayList<String> respuestas = new ArrayList<>();   
    private int indicePregunta = 0;
    
     // Listas de síntomas por enfermedad
    private ArrayList<String> sintomasIntolerancia = new ArrayList<>();
    private ArrayList<String> sintomasCancerEstomago = new ArrayList<>();
    private ArrayList<String> sintomasCancerColon = new ArrayList<>();
    private ArrayList<String> sintomasComunes = new ArrayList<>();
    
    //Nuevos ArrayList
    private ArrayList<String> sintomasIntoEstomago = new ArrayList<>();
    private ArrayList<String> sintomasIntoColon = new ArrayList<>();
    private ArrayList<String> sintomasEstoColon = new ArrayList<>();
    
    private String nombre;
    private String edad;
    private String genero;
    
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
    
    public void obtenernombre(String nombre){
        this.nombre = nombre;
    }
    
    private void inicializarSintomas() {
        //Sintomas de dos enfermedades 
        //CANCER DE ESTOMAGO Y COLON
        sintomasEstoColon.add("¿Ha padecido de sangrado al ir al baño?");
        sintomasEstoColon.add("¿Ha perdido más peso de lo normal últimamente?");
        
        
        //CANCER DE ESTOMAGO E INTOLERANCIA A LA LACTOSA
        sintomasIntoEstomago.add("¿Se ha sentido hinchado o con distensión?");
        sintomasIntoEstomago.add("¿Ha sentido acidez o 'gruñidos' estomacales?");
        sintomasIntoEstomago.add("¿Sufre náuseas o vómitos últimamente?");
        
        //CANCER DE COLON E INTOLERANCIA A LA LACTOSA
        sintomasIntoColon.add("¿Ha sufrido de diarrea estos dias?");
        sintomasIntoColon.add("¿Padece de dolores abdominales estos días?");
        
        // Síntomas comunes
        sintomasComunes.add("¿Sufre de estreñimiento?");
        sintomasComunes.add("¿Tiene o ha tenido incontinencia?");
        sintomasComunes.add("¿Ha tenido cambios abruptos en su peso?");

        
        // Síntomas de intolerancia a la lactosa
        sintomasIntolerancia.add("¿Ha sentido que tiene gases últimamente?");
        sintomasIntolerancia.add("¿Ha sentido o siente ruidos estomacales?");


        // Síntomas de cáncer de estómago
        sintomasCancerEstomago.add("¿Ha tenido un cambio de color de piel, específicamente a amarillo?");
        sintomasCancerEstomago.add("¿Ha tenido o ha padecido de icteria?");
        sintomasCancerEstomago.add("¿Ha perdido la sensación del apetito?");
        sintomasCancerEstomago.add("¿Ha tenido los ojos amarillos?");       
        sintomasCancerEstomago.add("¿Ha sentido una fatiga excesiva?");
        sintomasCancerEstomago.add("¿Ha tenido o tiene problemas para tragar?");

        // Síntomas de cáncer de colon
        sintomasCancerColon.add("¿Ha sentido querer ir al baño aunque ya haya ido varias veces?");
        sintomasCancerColon.add("¿Ha sentido fatiga no de forma excesiva?");
        sintomasCancerColon.add("¿Ha visto estrechamiento en las heces cuando va al baño?");

    }
    
       private void inicializarPreguntas() {
        // Agregar todos los síntomas (sin duplicar preguntas)
        preguntas.addAll(sintomasComunes);
        preguntas.addAll(sintomasIntolerancia);
        preguntas.addAll(sintomasCancerEstomago);
        preguntas.addAll(sintomasCancerColon);
        preguntas.addAll(sintomasIntoEstomago);
        preguntas.addAll(sintomasIntoColon);
        preguntas.addAll(sintomasEstoColon);

        preguntas = new ArrayList<>(new java.util.LinkedHashSet<>(preguntas));
    }
         private void mostrarPregunta() {
    // Mostrar la pregunta actual en el jLabel2
    if (indicePregunta < preguntas.size()) {
        jLabel2.setText("<html>" + preguntas.get(indicePregunta) + "</html>");
    } else {
        // Verificar si todas las respuestas son "Sí"
        boolean todasSi = respuestas.stream().allMatch(respuesta -> "Sí".equalsIgnoreCase(respuesta));
        
        if (todasSi) {
            JOptionPane.showMessageDialog(
                this,
                "Has respondido 'Sí' a todas las preguntas, vuelva a realizar el test.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE
            );
            // Reiniciar el test
            reiniciarTest();
        } else {
            // Si no todas las respuestas son "Sí", mostrar diagnóstico
            StringBuilder resumen = new StringBuilder("Respuestas:\n");
            StringBuilder resumenporcentaje = new StringBuilder("\nPorcentajes probables:");
            String resultadosintoma = null;
            
            for (int i = 0; i < preguntas.size(); i++) {
                resumen.append(preguntas.get(i)).append(" Respuesta: ").append(respuestas.get(i)).append("\n");
            }

            // Calcular porcentajes por enfermedad
            double porcentajeIntolerancia = calcularPorcentaje(sintomasIntolerancia, sintomasComunes, sintomasIntoEstomago, sintomasIntoColon);
            double porcentajeCancerEstomago = calcularPorcentaje(sintomasCancerEstomago, sintomasComunes, sintomasIntoEstomago, sintomasEstoColon);
            double porcentajeCancerColon = calcularPorcentaje(sintomasCancerColon, sintomasComunes, sintomasEstoColon, sintomasIntoColon);
            
            
            
            
            
            
            double porcentajeTotal = porcentajeIntolerancia + porcentajeCancerEstomago + porcentajeCancerColon
                    ;
            
            if (porcentajeIntolerancia > porcentajeCancerEstomago && porcentajeIntolerancia > porcentajeCancerColon)
            {
                resultadosintoma = "Usted puede padecer de: \nIntolerancia a la lactosa";
            }
            else if (porcentajeCancerEstomago > porcentajeIntolerancia && porcentajeCancerEstomago > porcentajeCancerColon)
            {
                resultadosintoma = "Usted puede padecer de: \nCáncer de estómago";
            }
            else if (porcentajeCancerColon > porcentajeIntolerancia && porcentajeCancerColon > porcentajeCancerEstomago)
            {
                resultadosintoma = "Usted puede padecer de: \nCáncer de colon";
            }
            else if (porcentajeIntolerancia == porcentajeCancerEstomago && porcentajeIntolerancia > porcentajeCancerColon)
            {
                resultadosintoma = "Usted puede estar padeciendo de: \nIntolerancia a la lactosa \nCáncer de estómago";
            }
            else if (porcentajeIntolerancia > porcentajeCancerEstomago && porcentajeCancerEstomago > porcentajeCancerColon)
            {
                resultadosintoma = "Usted puede estar padeciendo de: \nIntolerancia a la lactosa \nCáncer de estómago";
            }
            else if (porcentajeCancerEstomago == porcentajeCancerColon && porcentajeCancerEstomago > porcentajeIntolerancia)
            {
                resultadosintoma = "Usted puede estar padeciendo de: \nCáncer de estomago \nCáncer de colon";
            }
            else if (porcentajeCancerEstomago > porcentajeCancerColon && porcentajeCancerEstomago > porcentajeIntolerancia)
            {
                resultadosintoma = "Usted puede estar padeciendo de: \nCáncer de estomago \nCáncer de colon";
            }
            else if (porcentajeIntolerancia == porcentajeCancerColon && porcentajeIntolerancia > porcentajeCancerEstomago)
            {
                resultadosintoma = "Usted puede estar padeciendo de: \nIntolerancia a la lactosa \nCáncer de colon";
            }
            else if (porcentajeIntolerancia > porcentajeCancerColon && porcentajeCancerColon > porcentajeCancerEstomago)
            {
                resultadosintoma = "Usted puede estar padeciendo de: \nIntolerancia a la lactosa \nCáncer de colon";
            }
            
            else if (porcentajeIntolerancia == porcentajeCancerColon && porcentajeIntolerancia == porcentajeCancerEstomago)
            {
                resultadosintoma = "Usted puede estar padeciendo de: \nIntolerancia a la lactosa \nCáncer de estómago \nCáncer de colon";
            }
            
            if (porcentajeIntolerancia > 100.0 || porcentajeCancerEstomago > 100.0 || porcentajeCancerColon > 100.0)
            {
                JOptionPane.showMessageDialog(this, "Las probabilidades superaron el 100%, vuelva a realizar el test.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                reiniciarTest();
            }
            

            
            else if (porcentajeTotal == 0.0)
            {
                JOptionPane.showMessageDialog(this, "Ha respondido 'No' en todas las preguntas, vuelva a realizar el test.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                reiniciarTest();
            }
            else
            {
                // Mostrar porcentajes en el resumen
                resumenporcentaje.append("\n- Intolerancia a la lactosa: ").append(String.format("%.2f", porcentajeIntolerancia)).append("%");
                resumenporcentaje.append("\n- Cáncer de estómago: ").append(String.format("%.2f", porcentajeCancerEstomago)).append("%");
                resumenporcentaje.append("\n- Cáncer de colon: ").append(String.format("%.2f", porcentajeCancerColon)).append("%");

                // Mostrar resumen al usuario
                JOptionPane.showMessageDialog(this, resumen.toString(), "Resumen", JOptionPane.INFORMATION_MESSAGE);
                
                Diagnostico diagnostico = new Diagnostico();
                diagnostico.porcentajeresumen(resumenporcentaje.toString());
                diagnostico.sintomaresultado(resultadosintoma);
                diagnostico.nombreobtener(nombre);
                diagnostico.setVisible(true);
                this.dispose(); // Cerrar el JFrame actual
            }
        }
    }
}

private void reiniciarTest() {
    // Reiniciar las respuestas y el índice
    respuestas.replaceAll(respuesta -> ""); // Reinicia todas las respuestas a cadenas vacías
    indicePregunta = 0; // Reinicia el índice de las preguntas
    mostrarPregunta(); // Muestra la primera pregunta de nuevo
}


    private double calcularPorcentaje(ArrayList<String> sintomasEspecificos, ArrayList<String> sintomasComunes, 
            ArrayList<String> sintomasMixtos1, ArrayList<String> sintomasMixtos2) {
        int coincidenciaesp = 0;
        int coincidenciacom = 0;
        int coincidenciamix1 = 0;
        int coincidenciamix2 = 0;
        int coincidenciatot;
        int sintomastot;
       // double porcentajeesp;
        double porcentajetot;
        for (int i = 0; i < preguntas.size(); i++) {
            if (sintomasEspecificos.contains(preguntas.get(i)) && "Sí".equalsIgnoreCase(respuestas.get(i))) {
                coincidenciaesp++;
            }
        }
        
        for (int i = 0; i < preguntas.size(); i++) {
            if (sintomasComunes.contains(preguntas.get(i)) && "Sí".equalsIgnoreCase(respuestas.get(i))) {
                coincidenciacom++;
            }
        }
        
        for (int i = 0; i < preguntas.size(); i++) {
            if (sintomasMixtos1.contains(preguntas.get(i)) && "Sí".equalsIgnoreCase(respuestas.get(i))) {
                coincidenciamix1++;
            }
        }
        
        for (int i = 0; i < preguntas.size(); i++) {
            if (sintomasMixtos2.contains(preguntas.get(i)) && "Sí".equalsIgnoreCase(respuestas.get(i))) {
                coincidenciamix2++;
            }
        }
        coincidenciatot = coincidenciacom + coincidenciaesp + coincidenciamix1 + coincidenciamix2;
        sintomastot = sintomasEspecificos.size() + sintomasComunes.size() + sintomasMixtos1.size() + sintomasMixtos2.size();
        //calculo el porcentaje
        
        porcentajetot = (coincidenciatot * 100.0)/sintomastot;
        
        return porcentajetot;
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
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 610, 50));

        jButton1.setBackground(new java.awt.Color(102, 255, 102));
        jButton1.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jButton1.setText("SI");
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 210, 80, 50));

        jButton2.setBackground(new java.awt.Color(255, 102, 102));
        jButton2.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jButton2.setText("NO");
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 80, 50));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/02_Jun_2012_14_26_08_house1_1.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 640, 350));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel5.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 230, 150));

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel6.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 0, 230, 150));

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
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
