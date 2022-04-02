package com;

import java.io.*;
import java.util.ArrayList;

public class Decompression extends AdiustingNodes {

    ArrayList<Node> nodes;
    String data;
    String Code;
    int longest=0;

    Decompression(){
        nodes = new ArrayList<>();
        data = new String();
    }

    public int searchCode(String current){              //it searches for the code...
        for(int i=0; i<nodes.size(); i++){
            if((current.equals(nodes.get(i).code)) == true)
                return i;
        }
        return -1;
    }

    public void Decompress(String c){
        Code = c;
        String current = "";
        for(int i=0; i<Code.length(); i++){
            current += c.charAt(i);
            int index = searchCode(current);
            if(index != -1)
            {
                data += nodes.get(index).symbol;
                current="";
            }
            if(current.length() > longest){
                System.out.println("There is an error in code or the table!!!");
            }
        }
    }

    public void read(String Cpath, String Tpath){
        try{
            BufferedReader tableFile = new BufferedReader(new FileReader(new File(Tpath)));
            while(true){
                Node tmp = new Node();
                String line = tableFile.readLine();
                if((line.equals(File.separator)) == true)
                    break;
                String []tmpLine = line.split(",");
                tmp.symbol = tmpLine[0];
                tmp.code = tmpLine[1];
                nodes.add(tmp);
            }
            tableFile.close();
            getLongest();

            BufferedReader codeFile = new BufferedReader(new FileReader(new File(Cpath)));
            String data = codeFile.readLine();
            Decompress(data);
            codeFile.close();
            Write();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void Write(){
        try{
            BufferedWriter br = new BufferedWriter(new FileWriter(new File("Decompressed_Data.txt")));
            br.write(data);
            br.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void getLongest(){
        for(int i=0; i<nodes.size(); i++){
            if(nodes.get(i).code.length() > longest) {longest = nodes.get(i).code.length();}
        }
    }
}
