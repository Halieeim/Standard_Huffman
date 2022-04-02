package com;

import java.io.IOException;
import java.util.ArrayList;

public class AdiustingNodes {

    ArrayList<Node> nodes;
    ArrayList<String> Symbols;

    AdiustingNodes(){
        nodes = new ArrayList<>();
        Symbols = new ArrayList<>();
    }

    public void AdjustNodes(String data){
        calcFreqs(data);
        sNodes();
        for(int i = nodes.size()-1; i > 1; i--)
        {
            Node tmp = new Node();
            tmp.symbol += nodes.get(i).symbol + nodes.get(i-1).symbol;
            tmp.freq = nodes.get(i).freq + nodes.get(i-1).freq;
            if(nodes.get(i-1).symbol.length() == 1 ){
                nodes.get(i-1).left = null;
                nodes.get(i-1).right = null;
            }
            else if(nodes.get(i).symbol.length() == 1){
                nodes.get(i).left = null;
                nodes.get(i).right = null;
            }
            tmp.left = nodes.get(i-1);
            tmp.right = nodes.get(i);
            nodes.add(tmp);
            sNode(tmp);
        }
        coding();
    }

    public void calcFreqs(String data) throws NullPointerException{
        Symbols = new ArrayList<>();
        for(int i=0; i<data.length(); i++)
        {
            String current = "";
            current += data.charAt(i);
            int index = searchIndex(current);
            if(index == -1){
                Node node = new Node();
                node.symbol += current;
                node.freq = 1;
                nodes.add(node);
            } else{
                nodes.get(index).freq++;
            }
        }
    }

    public void sNodes(){
        for(int i = 0; i < nodes.size()-1; i++)
        {
            int greatest = i;

            //Determining the index of greatest frequency of all nodes(symbols)...
            for(int j = i + 1; j < nodes.size(); j++) {
                if (nodes.get(j).freq > nodes.get(greatest).freq)
                    greatest = j;
            }

            //Swapping...
            Node tmp = new Node();
            tmp = nodes.get(i);
            nodes.set(i, nodes.get(greatest));
            nodes.set(greatest, tmp);
        }
    }

    public void sNode(Node tmp){
        int i;
        for(i = 0; i < nodes.size(); i++){
            if (nodes.get(i).freq == tmp.freq && tmp.symbol.length() < nodes.get(i).symbol.length()) {
                break;
            }

            if(nodes.get(i).freq < tmp.freq) {
                break;
            }
        }

        for(int j = nodes.size()-1; j > i; j--) //shifting nodes
        {
            Node tmp2 = nodes.get(j-1);
            nodes.set(j - 1, nodes.get(j));
            nodes.set(j, tmp2);
        }
        nodes.set(i, tmp);
    }

    public int searchIndex(String symbol){           //it searches for the symbol...
        for(int i=0; i<nodes.size(); i++)
            if((symbol.equals(nodes.get(i).symbol)) == true)
                return i;
        return -1;
    }

    public void coding(){
        nodes.get(0).code += "1";
        nodes.get(1).code += "0";
        for(int i=0; i<nodes.size(); i++){
            if(nodes.get(i).right != null){
                nodes.get(i).right.code += nodes.get(i).code + "0";
            }

            if(nodes.get(i).left != null){
                nodes.get(i).left.code += nodes.get(i).code + "1";
            }
        }
    }

    public ArrayList<Node> GetNodes(){
        return nodes;
    }

    public double calcCompressionRatio(String content, ArrayList<Node> nodes) throws IOException {
        double sizeOfCompressedSymbols = 0;
        for (int i = 0; i < nodes.size(); i++){ //calculating the size of compressed code...
            if (nodes.get(i).symbol.length() == 1)
                sizeOfCompressedSymbols += nodes.get(i).freq * nodes.get(i).code.length();
        }
        double sizeOfOriginalText = content.length() * 8;
        System.out.println("size of compressed text = " + sizeOfCompressedSymbols);
        System.out.println("size of original text = " + sizeOfOriginalText);
        double compressionRatio =  sizeOfOriginalText / sizeOfCompressedSymbols;
        return compressionRatio;
    }

}
