package Enums;

/**
 *
 * @author David
 */
public enum Course {
    
    Media_Design,
    Software_Engineering,
    Business,
    Techniek;
    
    /**
     * @param course
     * 
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
