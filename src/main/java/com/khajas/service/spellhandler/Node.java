/*
 * Copyright (c) 2017 Anwar.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Anwar - initial API and implementation and/or initial documentation
 */
package com.khajas.service.spellhandler;

import java.util.ArrayList;

/**
 *
 * @author Anwar
 */
/**
 * The Following class defines each node for a trie
 * @author Anwar
 */
class Node{
    private final char ch;
    private final ArrayList<Node> childNodes;
    /**
     * Constructs Node for a given character
     * @param ch
     * 		Character for which node has to be made
     */
    Node(char ch){
        this.ch=ch;
        childNodes=new ArrayList<>();
    }
    /**
     * Add a child node
     * @param ch	- Character
     * 		Child node that should be added
     * @return	node
     * 		Child node that is added
     */
    public Node addChild(char ch){
        ch=Character.toLowerCase(ch);
        if(hasChild(ch))
            return getNode(ch);
        Node node=new Node(ch);
        childNodes.add(node);
        return node;
    }
    /**
     * Gives all child nodes from given node
     * @return	arrayListOfChildNodes
     * 		Returns an array list of all child nodes
     */
    public ArrayList<Node> getAllChildNodes(){
        return new ArrayList<>(this.childNodes);
    }
    /**
     * Checks if node has given child
     * @param ch
     * 		Character 'ch'
     * @return boolean
     * 		Returns true if given there is a child else returns false
     */
    public boolean hasChild(char ch){
        for(Node node: childNodes)
            if(node!=null)
                if(node.getChar()==ch)
                    return true;
        return false;
    }
    /**
     * Returns the character the belongs to this node
     * @return ch
     * 		Character that belongs to this node
     */
    public char getChar(){
        return ch;
    }
    /**
     * Returns the node for a child character 'ch'
     * @param ch
     * 		Character whose node has to be returned
     * @return
     * 		Returns node that belongs to character 'ch'
     */
    public Node getNode(char ch){
        if(!hasChild(ch)) return null;
        for(Node n: childNodes)
            if(n!=null)
                if(n.getChar()==ch)
                    return n;
        return null;
    }
}
//////////////////////		END OF SOURCE FILE		///////////////////////