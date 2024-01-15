package ca.ulaval.glo2004.domain;

public enum ToitDirection {
    FRONT_TO_BACK,
    LEFT_TO_RIGHT,
    RIGHT_TO_LEFT,
    BACK_TO_FRONT;


    public static ToitDirection fromString(String str){
        switch (str){
            case "Arriere_Avant":
                return BACK_TO_FRONT;
            case "Avant_Arriere":
                return FRONT_TO_BACK;
            case "Gauche_Droite":
                return LEFT_TO_RIGHT;
            case "Droite_Gauche":
                return RIGHT_TO_LEFT;
            default:
                throw new IllegalArgumentException("Introuvable: " + str);
        }
    }
}
