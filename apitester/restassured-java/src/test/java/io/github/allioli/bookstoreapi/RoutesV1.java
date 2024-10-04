package io.github.allioli.bookstoreapi;

public class RoutesV1 {
    private static final String BOOKSTORE = "/BookStore";
    private static final String ACCOUNT = "/Account";
    private static final String VERSION = "/v1";

    // Example /Account/v1/GenerateToken
    public static String generateToken(){
        return ACCOUNT + VERSION + "/GenerateToken";
    }

    // Example /Account/v1/Authorized
    public static String userAuthorised(){
        return ACCOUNT + VERSION + "/Authorized";
    }

    // Example /BookStore/v1/Books
    public static String books(){
        return BOOKSTORE + VERSION + "/Books";
    }

    // Example /BookStore/v1/Book
    public static String book(){
        return BOOKSTORE + VERSION + "/Book";
    }

    // Example /Account/v1/User/5fa127c4-1f2b-4ccd-b8f2-e0620f562da8
    public static String userAccount(String userId){
        return ACCOUNT + VERSION + "/User" + "/" + userId;
    }

}
