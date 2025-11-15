import React from "react";
import '../css/Products.css'
import ProductItem from "./ProductItem";
export default function Products() {
    return (
        <div id="menu-products-block">
            <h1 className="title" style={{ textAlign: "start" }}>Get inspired</h1>
            <div className="collection-buttons">
                <button className="button">Get started</button>
                <button className="button-transparent">Book a demo</button>
                <button className="button">Get started</button>
                <button className="button">Get started</button>
                <button className="button">Get started</button>
            </div>
            <div id="products">
                    <ProductItem
                        image={"https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=400"}
                        name={"Premium Wireless Headphones"}
                        description={"High-fidelity audio with active noise cancellation and 30-hour battery life"}
                        price={299.99}
                        rating={4.5}
                    />
                    <ProductItem
                        image={"https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=400"}
                        name={"Classic Analog Watch"}
                        description={"Elegant timepiece with leather strap and sapphire crystal"}
                        price={189.00}
                        rating={4.8}
                    />
                    <ProductItem
                        image={"https://images.unsplash.com/photo-1572635196237-14b3f281503f?w=400"}
                        name={"Designer Sunglasses"}
                        description={"UV protection with polarized lenses and titanium frame"}
                        price={159.99}
                        rating={4.3}
                    />
                    <ProductItem
                        image={"https://images.unsplash.com/photo-1606107557195-0e29a4b5b4aa?w=400"}
                        name={"Running Shoes Pro"}
                        description={"Lightweight cushioning for maximum comfort and performance"}
                        price={129.99}
                        rating={4.7}
                    />
                    <ProductItem
                        image={"https://images.unsplash.com/photo-1585386959984-a4155224a1ad?w=400"}
                        name={"Leather Backpack"}
                        description={"Handcrafted genuine leather with laptop compartment"}
                        price={219.00}
                        rating={4.6}
                    />
                    <ProductItem
                        image={"https://images.unsplash.com/photo-1611930022073-b7a4ba5fcccd?w=400"}
                        name={"Mechanical Keyboard"}
                        description={"RGB backlit with tactile switches for gaming and typing"}
                        price={149.99}
                        rating={4.9}
                    />
                    <ProductItem
                        image={"https://images.unsplash.com/photo-1484704849700-f032a568e944?w=400"}
                        name={"Smart Fitness Watch"}
                        description={"Heart rate monitor, GPS tracking, and sleep analysis"}
                        price={279.00}
                        rating={4.4}
                    />
                    <ProductItem
                        image={"https://images.unsplash.com/photo-1560343090-f0409e92791a?w=400"}
                        name={"Professional Camera"}
                        description={"24MP sensor with 4K video and interchangeable lenses"}
                        price={1299.99}
                        rating={4.8}
                    />
                    <ProductItem
                        image={"https://images.unsplash.com/photo-1588508065123-287b28e013da?w=400"}
                        name={"Portable Speaker"}
                        description={"Waterproof Bluetooth speaker with 360° sound"}
                        price={89.99}
                        rating={4.5}
                    />
            </div>
        </div>
    );
}