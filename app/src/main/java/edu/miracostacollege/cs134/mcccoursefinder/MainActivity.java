package edu.miracostacollege.cs134.mcccoursefinder;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import edu.miracostacollege.cs134.mcccoursefinder.model.Course;
import edu.miracostacollege.cs134.mcccoursefinder.model.DBHelper;
import edu.miracostacollege.cs134.mcccoursefinder.model.Instructor;
import edu.miracostacollege.cs134.mcccoursefinder.model.Offering;

public class MainActivity extends AppCompatActivity {

    private DBHelper db;
    private static final String TAG = "MCC Course Finder";

    private List<Instructor> allInstructorsList;
    private List<Course> allCoursesList;
    private List<Offering> allOfferingsList;
    private List<Offering> filteredOfferingsList;

    private EditText courseTitleEditText;
    private Spinner instructorSpinner;
    private ListView offeringsListView;
    private OfferingListAdapter offeringsListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deleteDatabase(DBHelper.DATABASE_NAME);
        db = new DBHelper(this);
        db.importCoursesFromCSV("courses.csv");
        db.importInstructorsFromCSV("instructors.csv");
        // DONE: use import OfferingsFromCSV
        db.importOfferingsFromCSV("offerings.csv");

        allCoursesList = db.getAllCourses();
        allInstructorsList = db.getAllInstructors();
        allOfferingsList = db.getAllOfferings();
        // Make a copy in filteredOfferingsList
        filteredOfferingsList = new ArrayList<>(allOfferingsList);

        //TODO (1): Construct instructorSpinnerAdapter using the method getInstructorNames()
        //TODO: to populate the spinner.
        instructorSpinner = findViewById(R.id.instructorSpinner);
        final ArrayAdapter<String> instructorSpinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, getInstructorNames());
        instructorSpinner.setAdapter(instructorSpinnerAdapter);
        instructorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get instructor at position in the parent
                String selectedInstructorName = String.valueOf(parent.getItemAtPosition(position));
                // Check to see if selected instructor is "[Select Instructor]"
                if (position > 0)
                {
                    // Clear the list adapter first, then filter
                    offeringsListAdapter.clear();
                    // Loop through offeringsList, try to find selectedInstructorName
                    for (Offering offering : allOfferingsList)
                    {
                        Instructor instructor = offering.getInstructor();
                        if (instructor.getFullName().equalsIgnoreCase(selectedInstructorName))
                        {
                            offeringsListAdapter.add(offering);
                        }
                    }
                }
                else
                {
                    offeringsListAdapter.clear();
                    offeringsListAdapter.addAll(allOfferingsList);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Wire up the ListView
        offeringsListView = findViewById(R.id.offeringsListView);
        offeringsListAdapter = new OfferingListAdapter(this, R.layout.offering_list_item, filteredOfferingsList);
        // Connect list view to list adapter
        offeringsListView.setAdapter(offeringsListAdapter);

        // Wire up EditText
        courseTitleEditText = findViewById(R.id.courseTitleEditText);
        courseTitleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = s.toString();
                // Clear list
                offeringsListAdapter.clear();

                if (searchText.equals(""))
                {
                    offeringsListAdapter.addAll(allOfferingsList);
                    return;
                }
                for (Offering offering : allOfferingsList)
                {
                    if (offering.getCourse().getTitle().contains(searchText))
                    {
                        offeringsListAdapter.add(offering);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    //DONE (2): Create a method getInstructorNames that returns a String[] containing the entry
    //TODO: "[SELECT INSTRUCTOR]" at position 0, followed by all the full instructor names in the
    //TODO: allInstructorsList
    private String[] getInstructorNames()
    {
        String[] instructorNames = new String[allInstructorsList.size() + 1];
        instructorNames[0] = "[Select Instructor]";
        for (int i = 1; i < instructorNames.length; i++) {
            instructorNames[i] = allInstructorsList.get(i - 1).getFullName();
        }
        return instructorNames;
    }


    //TODO (3): Create a void method named reset that sets the test of the edit text back to an
    //TODO: empty string, sets the selection of the Spinner to 0 and clears out the offeringListAdapter,
    //TODO: then rebuild it with the allOfferingsList



    //TODO (4): Create a TextWatcher named courseTitleTextWatcher that will implement the onTextChanged method.
    //TODO: In this method, set the selection of the instructorSpinner to 0, then
    //TODO: Clear the offeringListAdapter
    //TODO: If the entry is an empty String "", the offeringListAdapter should addAll from the allOfferingsList
    //TODO: Else, the offeringListAdapter should add each Offering whose course title starts with the entry.



    //TODO (5): Create an AdapterView.OnItemSelectedListener named instructorSpinnerListener and implement
    //TODO: the onItemSelected method to do the following:
    //TODO: If the selectedInstructorName != "[Select Instructor]", clear the offeringListAdapter,
    //TODO: then rebuild it with every Offering that has an instructor whose full name equals the one selected.
}
