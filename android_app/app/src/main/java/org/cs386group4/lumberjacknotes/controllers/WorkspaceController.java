package org.cs386group4.lumberjacknotes.controllers;

import android.content.Context;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.cs386group4.lumberjacknotes.R;
import org.cs386group4.lumberjacknotes.models.Notes;
import org.cs386group4.lumberjacknotes.models.Save;
import org.cs386group4.lumberjacknotes.models.UserProfile;
import org.cs386group4.lumberjacknotes.ui.MainActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class WorkspaceController
{
    Notes currentNote;

//    UserProfile dummyUserProfile = new UserProfile();
    UserProfile dummyUserProfile = UserProfile.getInstance();

    public WorkspaceController(MainActivity mainActivity, int notePosition)
    {
        loadNotes(mainActivity);
        initNewNoteButton(mainActivity);

        currentNote = dummyUserProfile.getWrittenNotes().get(notePosition);
    }

    private void initNewNoteButton(MainActivity mainActivity)
    {
        FloatingActionButton newNoteButton = mainActivity.findViewById(R.id.new_note_button);

        newNoteButton.setOnClickListener(view ->
        {
            // TODO: Create new note
            new Notes(UserProfile.getInstance());
            System.out.println(UserProfile.getInstance().getWrittenNotes().size());
        });
    }

    private void loadNotes(MainActivity mainActivity)
    {
        try
        {
            // Opens file where the UserProfile will be stored locally
            FileInputStream fileInputStream = mainActivity.openFileInput("UserProfile.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            // Opens UserProfile object from file and closes read access
            dummyUserProfile = (UserProfile) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        }
        catch(IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    public void saveNoteToStorage(MainActivity mainActivity)
    {
        try
        {
            // Creates file where the UserProfile will be stored locally
            FileOutputStream fileOutputStream = mainActivity.openFileOutput("UserProfile.txt", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            // Writes UserProfile object to file and closes write access
            objectOutputStream.writeObject(dummyUserProfile);
            objectOutputStream.close();
            fileOutputStream.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

    }

    public Notes getCurrentNote()
    {
        return currentNote;
    }
}
