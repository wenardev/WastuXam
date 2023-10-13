package com.example.wastuxam;

import android.util.ArrayMap;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DbQuery {

    public static FirebaseFirestore g_firestore;
    public static List<MatakuliahModel> g_matList = new ArrayList<>();
    public static int g_selected_mat_index = 0;

    public static List<LevelModel> g_levelList = new ArrayList<>();
    public static int g_selected_level_index = 0;

    public static List<LatihanModel> g_latihanList = new ArrayList<>();

    public static ProfileModel myProfile = new ProfileModel("NA", null);

    public static final int NOT_VISITED = 0;
    public static final int UNANSWERED = 1;
    public static final int ANSWERED = 2;
    public static final int REVIEW = 3;

    public static void createUserData(String email, String name, final MyCompleteListener completeListener)
    {
        Map<String, Object> userData = new ArrayMap<>();

        userData.put("EMAIL_ID",email);
        userData.put("NAME",name);
        userData.put("TOTAL_SCORE",0);

        DocumentReference userDoc = g_firestore.collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getUid());

        WriteBatch batch = g_firestore.batch();

        batch.set(userDoc,userData);

        DocumentReference countDoc = g_firestore.collection("USERS").document("TOTAL_USERS");
        batch.update(countDoc, "COUNT", FieldValue.increment(1));


        batch.commit()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        completeListener.onSuccess();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        completeListener.onFailure();
                    }
                });
    }

    public static void getUserData(final MyCompleteListener completeListener)
    {
        g_firestore.collection("USERS").document(FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        myProfile.setName(documentSnapshot.getString("NAME"));
                        myProfile.setEmail(documentSnapshot.getString("EMAIL_ID"));

                        completeListener.onSuccess();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        completeListener.onFailure();
                    }
                });
    }

    public static void loadMatakuliah(final MyCompleteListener completeListener)
    {
        g_matList.clear();

        g_firestore.collection("LATIHAN").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        Map<String, QueryDocumentSnapshot> docList = new ArrayMap<>();

                        for (QueryDocumentSnapshot doc: queryDocumentSnapshots)
                        {
                            docList.put(doc.getId(), doc);
                        }

                        QueryDocumentSnapshot matListDooc = docList.get("Matakuliah");

                        Long matCount  = matListDooc.getLong("COUNT");
                        for (int i=1; i <= matCount; i++)
                        {
                            String matID = matListDooc.getString("MAT" + String.valueOf(i) + "_ID");

                            QueryDocumentSnapshot matDoc = docList.get(matID);

                            int noOfLevels = matDoc.getLong("NO_OF_LEVELS").intValue();

                            String matName = matDoc.getString("NAME");
                            g_matList.add(new MatakuliahModel(matID, matName, noOfLevels));
                        }

                        completeListener.onSuccess();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {

                        completeListener.onFailure();

                    }
                });
    }

    public static void loadlatihan(final MyCompleteListener completeListener)
    {
        g_latihanList.clear();
        g_firestore.collection("LatihanQuest")
                .whereEqualTo("MATAKULIAH", g_matList.get(g_selected_mat_index).getDocID())
                .whereEqualTo("LEVEL", g_levelList.get(g_selected_level_index).getLevelID())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (DocumentSnapshot doc: queryDocumentSnapshots)
                        {
                            g_latihanList.add(new LatihanModel(
                                    doc.getString("LATIHANQ"),
                                    doc.getString("A"),
                                    doc.getString("B"),
                                    doc.getString("C"),
                                    doc.getString("D"),
                                    doc.getLong("ANSWER").intValue(),
                                    -1,
                                    NOT_VISITED
                            ));

                        }

                        completeListener.onSuccess();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        completeListener.onFailure();

                    }
                });
    }

    public static void loadLevelData(final MyCompleteListener completeListener)
    {
        g_levelList.clear();

        g_firestore.collection("LATIHAN").document(g_matList.get(g_selected_mat_index).getDocID())
                .collection("LEVELS_LIST").document("LEVELS_INFO")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        int noOfLevels = g_matList.get(g_selected_mat_index).getNoOfLevels();

                        for (int i=1; i <= noOfLevels; i++)
                        {
                            g_levelList.add(new LevelModel(
                                    documentSnapshot.getString("LEVEL" + String.valueOf(i) + "_ID"),
                                    0,
                                    documentSnapshot.getLong("LEVEL" + String.valueOf(i) + "_TIME").intValue()
                            ));
                        }

                        completeListener.onSuccess();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {

                        completeListener.onFailure();

                    }
                });


    }

    public static void loadData(final MyCompleteListener completeListener)
    {
        loadMatakuliah(new MyCompleteListener() {
            @Override
            public void onSuccess() {

                getUserData(completeListener);

            }

            @Override
            public void onFailure() {

                completeListener.onFailure();
            }
        });
    }

}
