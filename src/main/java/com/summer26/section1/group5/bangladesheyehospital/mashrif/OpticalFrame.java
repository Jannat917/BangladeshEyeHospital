package com.summer26.section1.group5.bangladesheyehospital.mashrif;

import java.io.Serializable;

public class OpticalFrame implements Serializable {
    private static final long serialVersionUID = 1L;

    private String frameId;
    private String frameName;
    private String frameShape;
    private String material;
    private String color;
    private double price;
    private int stockQuantity;

    public OpticalFrame() {}

    public OpticalFrame(String frameId, String frameName, String frameShape,
                        String material, String color, double price, int stockQuantity) {
        this.frameId = frameId;
        this.frameName = frameName;
        this.frameShape = frameShape;
        this.material = material;
        this.color = color;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public boolean isAvailable() { return stockQuantity > 0; }

    public void addStock(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be greater than zero.");
        stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be greater than zero.");
        if (quantity > stockQuantity) throw new IllegalArgumentException("Not enough frame stock.");
        stockQuantity -= quantity;
    }

    public String getFrameId() { return frameId; }
    public void setFrameId(String frameId) { this.frameId = frameId; }
    public String getFrameName() { return frameName; }
    public void setFrameName(String frameName) { this.frameName = frameName; }
    public String getOpticalFrameshape() { return frameShape; }
    public void setFrameShape(String frameShape) { this.frameShape = frameShape; }
    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    @Override
    public String toString() {
        return frameId + " - " + frameName;
    }
}
