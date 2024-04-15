package wajda9ni.dev.chefside;

import java.util.Arrays;

public class Commande {
    int id;
    String foodItem ;
    String foodType;
    String toppings;
    Double price;

    public Commande(int id,String foodItem, String foodType, String toppings,Double price) {
        this.id = id;
        this.foodItem = foodItem;
        this.foodType = foodType;
        this.toppings = toppings;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(String foodItem) {
        this.foodItem = foodItem;
    }


    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getToppings() {
        return toppings;
    }

    public void setToppings(String toppings) {
        this.toppings = toppings;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", foodItem='" + foodItem + '\'' +
                ", foodType='" + foodType + '\'' +
                ", toppings='" + toppings + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
