package com.google.engedu.ghost;

import java.util.HashMap;
import java.util.Random;


public class TrieNode {
    private HashMap<String, TrieNode> children;
    private boolean isWord;
    Random r = new Random();

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
    }

    public void add(String s) {
        int i;
        char ch;
        HashMap<String, TrieNode> t = children;
        for (i = 0; i < s.length(); i++) {
            ch = s.charAt(i);

            if (t != null && t.containsKey(ch + "")) {
                t = t.get(ch + "").children;
                continue;
            } else {
                while (i < s.length()) {
                    t.put(ch + "", new TrieNode());
                    t = t.get(ch + "").children;
                    i++;
                    if (i < s.length())
                        ch = s.charAt(i);
                    else
                        t.put("1", null);
                }
                break;
            }


        }
    }


    public boolean isWord(String s) {

        HashMap<String, TrieNode> t = children;
        for (int i = 0; i < s.length(); i++) {
            if (t.containsKey(s.charAt(i) + "")) {

                t = t.get(s.charAt(i) + "").children;

            } else
                return false;

        }
        if (t.containsKey("1"))
            return true;
        else
            return false;
    }

    public String getAnyWordStartingWith(String s) {
        if (s.equals("")) {

            while (true) {
                char a = (char) (97 + (r.nextInt(26)));
                if (children.containsKey(a + ""))
                    return (a + "");
            }//if no prefix
        } else {
            String st = "";
            HashMap<String, TrieNode> t = children;
            for (int i = 0; i < s.length(); i++) {
                if (t.containsKey(s.charAt(i) + "")) {
                    st = st + s.charAt(i);
                    t = t.get(s.charAt(i) + "").children;
                } else
                    return null;
            }
            if (st.length() == s.length()) {
                for (char ch = 'a'; ch <= 'z'; ch++) {
                    if (t.containsKey(ch + "")) {
                        st = st + ch;
                        t = t.get(ch + "").children;
                    }
                }
            }
            if (st.length() == s.length())
                return null;
            while (!(t.containsKey("1"))) {
                for (char ch = 'a'; ch <= 'z'; ch++) {
                    if (t.containsKey(ch + "")) {
                        st = st + ch;
                        t = t.get(ch + "").children;
                    }
                }
            }
            return st;
        }
    }

    public String getGoodWordStartingWith(String s) {
        if (s.equals("")) {

            while (true) {
                char a = (char) (97 + (r.nextInt(26)));
                if (children.containsKey(a + ""))
                    return (a + "");
            }//if no prefix
        } else {
            String st = "";
            HashMap<String, TrieNode> t = children;
            for (int i = 0; i < s.length(); i++) {
                if (t.containsKey(s.charAt(i) + "")) {
                    st = st + s.charAt(i);
                    t = t.get(s.charAt(i) + "").children;
                } else
                    return null;
            }
            if (st.length() == s.length()) {
                for (char ch = 'a'; ch <= 'z'; ch++) {
                    if (t.containsKey(ch + "")) {

                        if (isWord(st + ch))
                            continue;
                        st = st + ch;
                        t = t.get(ch + "").children;

                    }
                }
                if(st.length()!=s.length())
                    return st;
            }
            if (st.length() == s.length()) {
                for (char ch = 'a'; ch <= 'z'; ch++) {
                    if (t.containsKey(ch + "")) {
                        st = st + ch;
                        t = t.get(ch + "").children;
                    }
                }
                if(st.length()!=s.length())
                return st;
            }

        }
        return null;
    }

}

