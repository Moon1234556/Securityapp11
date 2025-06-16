package esfe.presentacion;

import javax.swing.*; // Importa el paquete javax.swing, que proporciona clases para crear interfaces gráficas de usuario (GUI) en Java Swing como ventanas, botones, cuadros de texto, etc.
import java.awt.event.WindowAdapter; // Importa la clase WindowAdapter desde el paquete java.awt.event.
import java.awt.event.WindowEvent;   // Importa la clase WindowEvent desde el paquete java.awt.event.

import esfe.dominio.User; // Importa la clase User desde el paquete esfe.dominio. Esta clase representa la entidad de usuario con sus atributos.
import esfe.persistencia.UserDao; // Importa la clase UserDAO desde el paquete esfe.Persistencia. Esta clase se encarga de la interacción con la base de datos.

/**
 * La clase LoginForm representa la ventana de inicio de sesión de la aplicación.
 * Extiende JDialog, lo que significa que es un cuadro de diálogo modal
 * que se utiliza para solicitar las credenciales del usuario (email y contraseña)
 * para acceder a la aplicación principal.
 */

public class LoginForm extends JDialog {
    private JPanel mainPanel;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnSalir;

    private UserDao userDao; // Declaración de una variable de instancia llamada 'userDAO' de tipo UserDAO. Esta variable se utilizará para interactuar con la capa de acceso a datos de usuarios.
    private MainForm mainForm; // Declaración de una variable de instancia llamada 'mainForm' de tipo MainFom. Esta variable representa la ventana principal de la aplicación.

    public LoginForm(MainForm mainForm) { // Constructor de la clase LoginForm que recibe una instancia de MainFom.
        this.mainForm = mainForm; // Asigna la instancia del formulario principal a la variable 'mainForm' de esta clase.
        userDao = new UserDao(); // Crea una nueva instancia de la clase UserDAO. Esta clase se encarga de la lógica de acceso a datos de los usuarios (CRUD).
        setContentPane(mainPanel); // Establece el panel principal ('mainPanel') como el contenido visible de este componente.
        setModal(true); // Establece un diálogo modal que bloquea la interacción con otras ventanas de la aplicación hasta que se cierra.
        setTitle("Login"); // Establece el título de la ventana como "Login".
        pack(); // Ajusta el tamaño de la ventana para que quepan todos sus componentes preferidos.
        setLocationRelativeTo(mainForm); // Centra la ventana de inicio de sesión relativamente al formulario principal ('mainForm').

        btnSalir.addActionListener(actionEvent -> System.exit(0)); // Agrega un ActionListener al botón 'btnSalir'. Cuando se hace clic en este botón, la aplicación se cierra.
        btnLogin.addActionListener(actionEvent -> login()); // Agrega un ActionListener al botón 'btnLogin'. Cuando se hace clic en este botón, se llama al método 'login()'.
        addWindowListener(new WindowAdapter() { // Agrega un WindowListener anónimo para manejar eventos de la ventana.
            @Override
            public void windowClosing(WindowEvent e) { // Sobreescribe el método 'windowClosing'. Este método se llama cuando el usuario intenta cerrar la ventana.
                System.exit(0); // Cierra la aplicación (terminando la JVM).
            }
        });
    }
    private void login() { // Método privado 'login' que se encarga de la lógica de autenticación.
        try {
            User user = new User(); // Crea una nueva instancia de la clase User para almacenar las credenciales del usuario.
            user.setEmail(txtEmail.getText()); // Obtiene el texto ingresado en el campo de texto 'txtEmail' y lo establece como el correo electrónico del usuario.
            user.setPasswordHash(new String(txtPassword.getPassword())); // Obtiene la contraseña ingresada en el campo de contraseña 'txtPassword' y la establece como el hash de la contraseña del usuario.

            User userAut = userDao.authenticate(user); // Llama al método 'authenticate' del objeto 'userDAO' para verificar las credenciales del usuario.

            // Verifica si la autenticación fue exitosa:
            // 1. 'userAut' no es null (se encontró un usuario).
            // 2. El ID del usuario autenticado es mayor que 0 (implica que es un usuario válido en la base de datos).
            // 3. El correo electrónico del usuario autenticado coincide con el correo electrónico ingresado.
            if (userAut != null && userAut.getId() > 0 && userAut.getEmail().equals(user.getEmail())) {
                this.mainForm.setUserAutenticate(userAut); // Si la autenticación es exitosa, establece el usuario autenticado en el formulario principal ('mainForm').
                this.dispose(); // Cierra la ventana de inicio de sesión actual.
            }
            else {
                // Si la autenticación falla, muestra un mensaje de diálogo de advertencia.
                JOptionPane.showMessageDialog(null, // El componente padre null para centrar en la pantalla.
                        "Email y password incorrecto",            // El mensaje que se muestra al usuario.
                        "Login", // El título de la ventana de diálogo.
                        JOptionPane.WARNING_MESSAGE); // El tipo de icono que se muestra (advertencia).
            }
        }
        catch (Exception ex) { // Captura cualquier excepción general que pueda ocurrir dentro del bloque try.
            // Captura cualquier excepción que pueda ocurrir durante el proceso de inicio de sesión (por ejemplo, error de base de datos).
            JOptionPane.showMessageDialog( null,
                    ex.getMessage(), // Muestra el mensaje de la excepción al usuario.
                    "Sistema",       // El título de la ventana de diálogo.
                    JOptionPane.ERROR_MESSAGE); // El tipo de icono que se muestra (error).
        }
    }
}