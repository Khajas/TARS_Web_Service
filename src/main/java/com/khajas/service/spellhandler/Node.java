/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khajas.service.spellhandler;

import java.util.ArrayList;

/**
 *
 * @author Anwar
 */
class Node{
        private final char ch;
        private final ArrayList<Node> childNodes;
        Node(char ch){
            this.ch=ch;
            childNodes=new ArrayList<>();
        }
        public Node addChild(char ch){
            ch=Character.toLowerCase(ch);
            if(hasChild(ch))
                return getNode(ch);
            Node n=new Node(ch);
            childNodes.add(n);
            return n;
        }
        public ArrayList<Node> getAllChildNodes(){
            return new ArrayList<>(this.childNodes);
        }
        public boolean hasChild(char ch){
            for(Node n: childNodes)
                if(n!=null)
                    if(n.getChar()==ch)
                        return true;
            return false;
        }
        public char getChar(){
            return ch;
        }
        public Node getNode(char ch){
            if(!hasChild(ch)) return null;
            for(Node n: childNodes)
                if(n!=null)
                    if(n.getChar()==ch)
                        return n;
            return null;
        }
    }