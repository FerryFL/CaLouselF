/**
 * 
 */
/**
 * 
 */
module CaLouselF {
	
	opens main;
	opens view.guest;
	opens view.admin;
	opens view.buyer;
	opens view.seller;
	opens controller;
	opens model;
	opens view_controller;
	opens database;
	
	requires java.sql;
	requires javafx.graphics;
	requires javafx.controls;
	requires javafx.base;
}