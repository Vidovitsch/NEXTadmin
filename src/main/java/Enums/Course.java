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
};
