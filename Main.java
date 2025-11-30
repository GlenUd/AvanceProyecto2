public class Main {

        private static GestorDonantes gestorDonantes = new GestorDonantes();
        private static GestorSolicitudes gestorSolicitudes = new GestorSolicitudes();

        public static GestorDonantes getGestorDonantes() {
            return gestorDonantes;
        }

        public static GestorSolicitudes getGestorSolicitudes() {
            return gestorSolicitudes;
        }
    }
