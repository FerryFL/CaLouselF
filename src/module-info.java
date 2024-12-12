/**
 * 
 */
/**
 * 
 */
module CaLouselF {
	requires java.sql;
	requires javafx.graphics;
	requires javafx.controls;
	
	opens main;
  opens view;
	opens util;
	opens controller;
	opens model;
	opens routes;
}