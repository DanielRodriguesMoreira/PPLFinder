package pt.isec.a21240321.pplfinder;

import android.graphics.Bitmap;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;

import pt.isec.a21240321.pplfinder.Constants.Constants;

/**
 * Created by drmor on 30/12/2016.
 */

public class User implements Constants{
    private String id;
    private String name;
    private int age;
    private String email;
    private String city;
    private Bitmap photo;
    private LatLng location;
    private ArrayList<String> musicInterests;
    private ArrayList<String> technologyInterests;
    private String club;
    private boolean alive;

    public User(){}

    public User(String id, String name, String email, boolean alive){
        this.setupProperties();

        this.setId(id);
        this.setName(name);
        this.setEmail(email);
        this.setAlive(alive);
    }

    private void setupProperties(){
        this.setId(null);
        this.setName(null);
        this.setAge(0);
        this.setEmail(null);
        this.setCity(null);
        this.setPhoto(null);
        this.setLocation(null);
        this.setMusicInterests(new ArrayList<String>());
        this.setTechnologyInterests(new ArrayList<String>());
        this.setClub(null);
        this.setAlive(false);
    }

    // <editor-fold defaultstate="collapsed" desc="Gets e Sets">
    public String getId() {
        return id;
    }

    public void setId(String id) {
        if(id == null || id.isEmpty())
            this.id = DEFAULT_ID;
        else
            this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(id == null || id.isEmpty())
            this.name = DEFAULT_NAME;
        else
            this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if(age < 0 || age > 120)
            this.age = DEFAULT_AGE;
        else
            this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(email == null || email.isEmpty())
            this.email = DEFAULT_EMAIL;
        else
            this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if(city == null || city.isEmpty())
            this.city = DEFAULT_CITY;
        else
            this.city = city;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public ArrayList<String> getMusicInterests() {
        return musicInterests;
    }

    public void setMusicInterests(ArrayList<String> musicInterests) {
        this.musicInterests = musicInterests;
    }

    public ArrayList<String> getTechnologyInterests() {
        return technologyInterests;
    }

    public void setTechnologyInterests(ArrayList<String> technologyInterests) {
        this.technologyInterests = technologyInterests;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        if(club == null || club.isEmpty())
            this.club = DEFAULT_CLUB;
        else
            this.club = club;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    // </editor-fold>
}
