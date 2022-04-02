package com;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Compression {

    ArrayList<Node> nodes;
    AdiustingNodes adjust = new AdiustingNodes();
    String data;
    String CompressedCode;


    Compression(){
        nodes = new ArrayList<>();
        CompressedCode = new String();
    }

    public void Compress(String Data){
        data = Data;
        adjust.AdjustNodes(Data);
        nodes = adjust.GetNodes();

        for(int i=0; i<Data.length(); i++)
        {
            String cur = "";
            cur += Data.charAt(i);
            for(int j=0; j<nodes.size(); j++)
                if((cur.compareTo(nodes.get(j).symbol)) == 0)
                {
                    CompressedCode += nodes.get(j).code;
                    break;
                }
        }
    }

    public void Read(String path){
        try{
            BufferedReader fr = new BufferedReader(new FileReader(new File(path)));
            String line = fr.readLine();
            Scanner scan = new Scanner(fr);
            while (scan.hasNextLine()) {
                line += scan.nextLine();
            }
            Compress(line);

            System.out.println("The length of data = " + line.length());
            System.out.println("The count of each character : ");
            for (int i = 0; i < nodes.size(); i++){
                if (nodes.get(i).symbol.length() == 1){
                    System.out.println(nodes.get(i).symbol
                            + " ==> " + nodes.get(i).freq);
                }
            }

            try{
                System.out.println("Compression ratio (the original sentence bit size / compress bit size) = " + adjust.calcCompressionRatio(line, nodes));
            }catch (IOException e){
                System.out.println("YOUR INPUT FILE IS EMPTY!!!");
            }

            Write();
            fr.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void Write(){
        try{
            BufferedWriter CodeFile = new BufferedWriter(new FileWriter(new File("Compressed_Code.txt")));
            CodeFile.write(CompressedCode);
            CodeFile.close();

            BufferedWriter TableFile = new BufferedWriter(new FileWriter(new File("Compressed_Table.txt")));
            for(int i=0; i<nodes.size(); i++){
                if(nodes.get(i).symbol.length() == 1){
                    String line = nodes.get(i).symbol + "," + nodes.get(i).code;
                    TableFile.write(line);
                    TableFile.newLine();
                }
            }
            TableFile.write(File.separator);
            TableFile.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
