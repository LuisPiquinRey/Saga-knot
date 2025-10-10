import React from 'react';

const Carousel = () => {
    return (
        <div id="carouselExample" className="carousel slide">
            <div className="carousel-inner">
                <div className="carousel-item active">
                    <img src="caroussel-image.jpg" className="carousel" alt="..." />
                </div>
                <div className="carousel-item">
                    <img src="caroussel-image-second.jpg" className="carousel" alt="..." />
                </div>
                <div className="carousel-item">
                    <img src="caroussel-image-third.jpg" className="carousel" alt="..." />
                </div>
            </div>
            <button className="carousel-control-prev" type="button" data-bs-target="#carouselExample" data-bs-slide="prev">
                <span className="carousel-control-prev-icon" aria-hidden="true"></span>
                <span className="visually-hidden">Previous</span>
            </button>
            <button className="carousel-control-next" type="button" data-bs-target="#carouselExample" data-bs-slide="next">
                <span className="carousel-control-next-icon" aria-hidden="true"></span>
                <span className="visually-hidden">Next</span>
            </button>
        </div>
    );
}

export default Carousel;
