package com.efhemo.baking.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class BakeResponse implements Parcelable {


    private int id;
    private String name;
    private List<Ingredients> ingredients;
    private List<Steps> steps;
    private int servings;
    private String image;

    public BakeResponse() {
    }

    public BakeResponse(int id, String name, List<Ingredients> ingredients, List<Steps> steps, int servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }


    protected BakeResponse(Parcel in) {
        id = in.readInt();
        name = in.readString();
        servings = in.readInt();
        image = in.readString();
    }

    public static final Creator<BakeResponse> CREATOR = new Creator<BakeResponse>() {
        @Override
        public BakeResponse createFromParcel(Parcel in) {
            return new BakeResponse(in);
        }

        @Override
        public BakeResponse[] newArray(int size) {
            return new BakeResponse[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public List<Steps> getSteps() {
        return steps;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public void setSteps(List<Steps> steps) {
        this.steps = steps;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.createTypedArrayList(Ingredients.CREATOR);
        dest.createTypedArrayList(Steps.CREATOR);
        dest.writeInt(servings);
        dest.writeString(image);
    }
}
