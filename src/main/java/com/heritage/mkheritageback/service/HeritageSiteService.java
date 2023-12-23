package com.heritage.mkheritageback.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.heritage.mkheritageback.model.HeritageSite;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class HeritageSiteService {
    Firestore dbFirestore = FirestoreClient.getFirestore();
    private final RestTemplate restTemplate;

    public HeritageSiteService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String createHeritageSite(HeritageSite HeritageSite) throws ExecutionException, InterruptedException {
        ApiFuture<DocumentReference> addedDocRef  = dbFirestore.collection("heritage_sites").add(HeritageSite);
        System.out.println("Added document with ID: " + addedDocRef.get().getId());
        return addedDocRef.get().getId();
    }

    public HeritageSite getHeritageSiteById(String id) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = dbFirestore.collection("heritage_sites").document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        HeritageSite heritageSite;
        if (document.exists()) {
            heritageSite = document.toObject(HeritageSite.class);
            heritageSite.setId(id);
            return heritageSite;
        }
        return null;
    }

    public List<HeritageSite> getHeritageSiteByName(String name) throws ExecutionException, InterruptedException {
        CollectionReference HeritageSites = dbFirestore.collection("heritage_sites");
        /*
        The character \uf8ff used in the query is a very high code point in the Unicode range (it is a Private Usage Area [PUA] code).
        Because it is after most regular characters in Unicode, the query matches all values that start with queryText.
         */
        Query query = HeritageSites.orderBy("name").startAt(name).endAt(name + '~'); // '~' == \uf8ff
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
        return documents.stream().map(i -> i.toObject(HeritageSite.class)).collect(Collectors.toList());
    }

    public List<HeritageSite> getAllHeritageSites() throws Exception {
        ApiFuture<QuerySnapshot> future = dbFirestore.collection("heritage_sites").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        return documents
                .stream()
                .map(i -> {
            HeritageSite heritageSite = i.toObject(HeritageSite.class);
            heritageSite.setId(i.getId());
            return heritageSite;
        })
                .collect(Collectors.toList());
    }

    public String updateHeritageSite(HeritageSite heritageSite) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> writeResultApiFuture = dbFirestore.collection("heritage_sites").document(heritageSite.getId()).set(heritageSite);
        return writeResultApiFuture.get().getUpdateTime().toString();
    }

    public String deleteHeritageSite(String id) {
        ApiFuture<WriteResult> writeResultApiFuture = dbFirestore.collection("heritage_sites").document(id).delete();
        return "Successfully deleted heritage site with id: " + id;
    }
}
