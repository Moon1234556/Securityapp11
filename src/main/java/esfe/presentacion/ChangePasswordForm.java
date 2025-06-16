package esfe.presentacion;

import esfe.dominio.User; // Importa la clase User, que probablemente representa un usuario en el sistema.
import esfe.persistencia.UserDao; // Importa la interfaz o clase UserDao, que define o implementa el acceso a datos para la entidad User.

import javax.swing.*;

public class ChangePasswordForm extends JDialog {
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnChangePassword;
    private JPanel mainPanel;

    private UserDao userDao; // Instancia de la clase UserDAO para interactuar con la base de datos de usuarios.
    private MainForm mainForm; // Referencia a la ventana principal de la aplicación.

    // Constructor de la clase ChangePasswordForm. Recibe una instancia de MainForm como parámetro.
    public ChangePasswordForm(MainForm mainForm) { //
        this.mainForm = mainForm; // Asigna la instancia de MainForm recibida a la variable local.
        userDao = new UserDao(); // Crea una nueva instancia de UserDao al instanciar este formulario.
        txtEmail.setText(mainForm.getUserAutenticate().getEmail()); // Pre-carga el campo de correo electrónico con el email del usuario autenticado.
        setContentPane(mainPanel); // Establece el panel principal como el contenido de este diálogo.
        setModal(true); // Hace que este diálogo sea modal, lo que significa que bloquea la interacción con la ventana principal hasta que se cierra.
        setTitle("Cambiar Password"); // Establece el título de la ventana del diálogo.
        pack(); // Ajusta el tamaño de la ventana para que todos sus componentes se muestren correctamente.
        setLocationRelativeTo(mainForm); // Centra la ventana del diálogo relativa a la ventana principal.

        // Agrega un ActionListener al botón btnChangePassword para que ejecute el método changePassword() cuando se haga clic.
        btnChangePassword.addActionListener(actionEvent -> changePassword());

    }
    private void changePassword() { // Método privado 'changePassword' que probablemente maneja la lógica para cambiar la contraseña de un usuario.
        try { // Bloque try para manejar posibles excepciones durante el proceso de cambio de contraseña.
            // Obtiene el usuario autenticado desde la ventana principal (MainForm).
            User userAut = mainForm.getUserAutenticate(); // Llama al método 'getUserAuthenticate()' de 'mainForm' para obtener el objeto User actualmente logueado.
            // Crea un nuevo objeto User para almacenar los datos de actualización.
            User user = new User(); // Se crea una nueva instancia de User, que probablemente se llenará con la nueva contraseña y otros datos para la actualización.
            // Establece el ID del usuario en el nuevo objeto User, utilizando el ID del usuario autenticado.
            user.setId(userAut.getId());
            // Establece la nueva contraseña en el objeto User, convirtiendo el array de caracteres del campo de contraseña a un String.
            user.setPasswordHash(new String(txtPassword.getPassword()));

           // Valida si la nueva contraseña está vacía.
            if (user.getPasswordHash().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "La contraseña es obligatoria",
                        "Validación", JOptionPane.WARNING_MESSAGE);
                return; // Sale del método si la contraseña está vacía.
            }

            // Intenta actualizar la contraseña del usuario en la base de datos a través del UserDao.
            boolean res = userDao.updatePassword(user);

            // Verifica el resultado de la actualización.
            if (res) {
                // Si la actualización es exitosa, cierra la ventana actual (ChangePasswordForm).
                this.dispose(); // Cierra el JDialog actual.

            // Crea una nueva instancia de la ventana de inicio de sesión (LoginForm), pasando la ventana principal como parámetro.
                LoginForm loginForm = new LoginForm(this.mainForm);

            // Hace visible la ventana de inicio de sesión.
                loginForm.setVisible(true); // Muestra la ventana de inicio de sesión para que el usuario pueda ingresar con la nueva contraseña.
            } else { // Si la actualización falla, muestra un mensaje de advertencia.
                JOptionPane.showMessageDialog(null,
                        "No se logro cambiar la contraseña",
                        "Cambiar contraseña", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) { // Captura cualquier excepción que ocurra durante el proceso.
            JOptionPane.showMessageDialog(null,
                    ex.getMessage(),
             "Sistema", JOptionPane.ERROR_MESSAGE); // Muestra un mensaje de error con la descripción de la excepción.
        }
    }
}