import React from "react";
import '../css/ProductItem.css'

export default function ProductItem({image, name, description, price, rating}){
    return (
        <div className="product-item">
            <div className="product-item-image-container">
                <img 
                    src={image} 
                    alt={name}
                    className="product-item-image"
                />
            </div>
            <div className="product-item-info">
                <h3 className="product-name">{name}</h3>
                <p className="product-description">{description}</p>
                <div className="product-footer">
                    <span className="product-price">${price}</span>
                    <div className="product-rating">
                        <span className="rating-stars">{'⭐'.repeat(Math.floor(rating))}</span>
                        <span className="rating-value">{rating}</span>
                    </div>
                </div>
            </div>
        </div>
    );
}