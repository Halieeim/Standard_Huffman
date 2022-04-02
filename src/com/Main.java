package com;

public class Main {

    public static void main(String[] args) {

        Compression c = new Compression();

        c.Read("Data.txt");
        System.out.println("Data Is Compressed Successfully.");

        Decompression dc = new Decompression();
        dc.read("Compressed_Code.txt", "Compressed_Table.txt");
        System.out.println("Data Is Decompressed Successfully.");

    }
}
