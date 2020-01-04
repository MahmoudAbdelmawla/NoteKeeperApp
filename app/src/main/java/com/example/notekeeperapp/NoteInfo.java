package com.example.notekeeperapp;

import android.os.Parcel;
import android.os.Parcelable;


public final class NoteInfo implements Parcelable {
    private CourseInfo mCourse;
    private String mTitle;
    private String mText;

    public NoteInfo(CourseInfo course, String title, String text) {
        mCourse = course;
        mTitle = title;
        mText = text;
    }

    private NoteInfo(Parcel parcel){

        /**
         * when we read back a Parcelable , we have to pass in the class loader information for that type
         */
        // A class Loader provides information on how to create instance of a type
        mCourse = parcel.readParcelable(CourseInfo.class.getClassLoader());
        mTitle = parcel.readString();
        mText = parcel.readString();

    }

    public CourseInfo getCourse() {
        return mCourse;
    }

    public void setCourse(CourseInfo course) {
        mCourse = course;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    private String getCompareKey() {
        return mCourse.getCourseId() + "|" + mTitle + "|" + mText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NoteInfo that = (NoteInfo) o;

        return getCompareKey().equals(that.getCompareKey());
    }

    @Override
    public int hashCode() {
        return getCompareKey().hashCode();
    }

    @Override
    public String toString() {
        return getCompareKey();
    }


    /**
     * the describeContents and writeToParcel takes care of saving side
     * describeContents method is used when we have special parceling needs
     * but we don't have any of those special needs so returning 0 is exactly what we want to do */

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * writeToParcel method is responsible to write the member information for the type instance into the parcel
     * and it receives a parcel as a parameter , so what we want to do is go through and write each member of
     * NoteInfo (The Class) into that parcel*/

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        // the course is a reference type ,and that means that course is to have to be parcelable.
        // to write a parcelable type into our parcel we use writeParcelable() method
        // the second parameter which allows us to pass a flag to indicate any special handling . we don't have so we'll just pass 0
        dest.writeParcelable(mCourse , 0);
        dest.writeString(mTitle);
        dest.writeString(mText);
    }

    /**
     * Now we need to add the code so it'll make our class re-creatable from parcel */

    public static final Parcelable.Creator<NoteInfo> CREATOR =
            new Parcelable.Creator<NoteInfo>() {
               /**
                * createFromParcel is where we create instance of our type and set all the values inside of it using the parcel
                * remember that when setting the values within createFromParcel
                * you must set them in the same order that you wrote the values within writeToParcel
                * because values stored in the parcel has no identifiers
                * the values simply written in and read back out in order */
                @Override
                public NoteInfo createFromParcel(Parcel source) {

                    // common technique we use when implementing createFromParcel is rather than setting the values directly within createFromParcel
                    // we instead use a private constructor
                    return new NoteInfo(source);
                }

                /**
                 * newArray is responsible to create an array of our type of the appropriate size
                 * newArray receives an integer parameter which indicates the desired size of the array*/
                @Override
                public NoteInfo[] newArray(int size) {
                    return new NoteInfo[size];
                }
            };


}
