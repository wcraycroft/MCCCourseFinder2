package edu.miracostacollege.cs134.mcccoursefinder.model;

/**
 * The <code>Course</code> class represents a single course at MiraCosta College,
 * including its alpha (e.g. CS), number (e.g. 134) and title (e.g. Mobile Application Development)
 *
 * @author Michael Paulding
 */
public class Course {
    private long mId;
    private String mAlpha;
    private String mNumber;
    private String mTitle;

    public Course(long id, String alpha, String number, String title) {
        mId = id;
        mAlpha = alpha;
        mNumber = number;
        mTitle = title;
    }

    public Course(String alpha, String number, String title) {
        this(-1, alpha, number, title);
    }

    public long getId() {
        return mId;
    }

    void setId(int id)
    {
        mId = id;
    }

    public String getAlpha() {
        return mAlpha;
    }


    public String getNumber() {
        return mNumber;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getFullName() {
        return mAlpha + " " + mNumber;
    }

    @Override
    public String toString() {
        return "Course{" +
                "Id=" + mId +
                ", Alpha='" + mAlpha + '\'' +
                ", Number='" + mNumber + '\'' +
                ", Title='" + mTitle + '\'' +
                '}';
    }
}
