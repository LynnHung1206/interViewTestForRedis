package com.lynn.redis_demo.intetviewTest.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class RandomUserResponse {
  private List<Result> results;


  @Data
  public static class Result {
    private String gender;
    private Name name;
    private Location location;
    private String email;
    private String phone;
    private String cell;


    @Data
    public static class Name {
      private String title;
      private String first;
      private String last;

    }

    @Data
    public static class Location {
      private Street street;
      private String city;
      private String state;
      private String country;
      private String postcode;


      @Data
      public static class Street {
        private int number;
        private String name;

      }
    }
  }
}
