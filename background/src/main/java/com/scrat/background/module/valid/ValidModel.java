package com.scrat.background.module.valid;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

public class ValidModel {
    @Size(min = 2, max = 16, message = "item_name length must be between 2 and 16")
    @NotBlank(message = "item_name require")
    private String itemName;
    @Email(message = "email is incorrect")
    @NotBlank(message = "email require")
    private String email;
    @Max(value = 100,message = "num must be less than 100")
    @Min(value = 1,message = "num must be bigger than 1")
    private Integer num;
    @Valid
    @NotEmpty(message = "items should not empty")
    private List<ValidItemModel> items;

    public String getItemName() {
        return itemName;
    }

    public ValidModel setItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ValidModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public Integer getNum() {
        return num;
    }

    public ValidModel setNum(Integer num) {
        this.num = num;
        return this;
    }

    public List<ValidItemModel> getItems() {
        return items;
    }

    public ValidModel setItems(List<ValidItemModel> items) {
        this.items = items;
        return this;
    }

    @Override
    public String toString() {
        return "ValidModel{" +
                "itemName='" + itemName + '\'' +
                ", email='" + email + '\'' +
                ", num=" + num +
                ", items=" + items +
                '}';
    }
}
