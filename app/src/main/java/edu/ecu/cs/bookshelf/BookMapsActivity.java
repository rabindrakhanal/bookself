package edu.ecu.cs.bookshelf;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String EXTRA_USERID = "userid";

    List<UserBook> bookList = new ArrayList<>();

    private BookBase bookBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        UUID userid = (UUID)getIntent().getSerializableExtra(EXTRA_USERID);

        bookList = UserBookBase.getUserBookBase(this).getUserBooks(userid);
        bookBase = BookBase.getBookBase(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                for(UserBook userbook: bookList){
                    Book book = bookBase.getBook(userbook.getBookId());
                    LatLng location = new LatLng(book.getLatitude(), book.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(location).icon(BitmapDescriptorFactory.defaultMarker()).title(book.getTitle()));
                    LatLngBounds bounds = new LatLngBounds.Builder().include(location).build();
                    CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bounds, 200);
                    mMap.animateCamera(update);
                }
            }
        });

    }
}
