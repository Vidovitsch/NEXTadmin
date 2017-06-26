package Enums;

/**
 * this enum contains all the main courses that IT students can attend at Fontys
 * @author David
 */
public enum Course {
    
    Media_Design,
    Software_Engineering,
    Business,
    Techniek;
    
    /**
     * returns the prefix associated with the given enum course
     * @param course
     * @return
     */
    public String getPrefix(Course course) {
        switch (course) {
            case Media_Design:
                return "M";
            case Software_Engineering:
                return "S";
            case Business:
                return "B";
            default:
                return "T";
        }
    }
    
    /**
     * This method returns the full name of the course
     * @param prefix
     * @return the full name of the course
     */
    public static Course getCourse(char prefix) {
        switch (prefix) {
            case 'M':
                return Media_Design;
            case 'S':
                return Software_Engineering;
            case 'B':
                return Business;
            default:
                return Techniek;
        }
    }
};
