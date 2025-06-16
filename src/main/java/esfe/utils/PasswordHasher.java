package esfe.utils;

import java.nio.charset.StandardCharsets;
// Clase que define juegos de caracteres estándar, como UTF-8, utilizado para codificar la contraseña antes de aplicar hashing

import java.security.MessageDigest;
// Clase que proporciona funcionalidades para algoritmos de resumen de mensajes criptográficos, como SHA-256

import java.security.NoSuchAlgorithmException;
// Clase para manejar excepciones que ocurren cuando un algoritmo criptográfico solicitado no está disponible en el entorno

import java.util.Base64;
// Clase utilitaria para codificar y decodificar datos en formato Base64, útil para representar hashes de manera legible


public class PasswordHasher {
        /**
         * Hashea una contraseña utilizando el algoritmo SHA-256 y la codifica en Base64.
         * @param password La contraseña en texto plano que se va a hashear.
         * @return Una cadena que representa la contraseña hasheada y codificada en Base64.
         *         Retorna null si el algoritmo SHA-256 no está disponible en el entorno.
         */
        public static String hashPassword(String password) {
            try {
                // Obtiene una instancia del algoritmo de resumen de mensajes SHA-256.
                MessageDigest digest = MessageDigest.getInstance("SHA-256");

                // Convierte la contraseña a bytes utilizando la codificación UTF-8 y calcula su hash.
                byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));

                // Codifica el array de bytes del hash resultante a una cadena utilizando la codificación Base64
                // para que sea fácilmente almacenable y transportable.
                return Base64.getEncoder().encodeToString(hashBytes);

            } catch (NoSuchAlgorithmException ex) {
                // Capture la excepción que ocurre si el algoritmo SHA-256 no está disponible..
                // En este caso, retorna null para indicar que et hasheo falló.
                return null;
            }
        }
    }
