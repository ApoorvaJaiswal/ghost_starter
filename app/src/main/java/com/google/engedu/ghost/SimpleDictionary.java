package com.google.engedu.ghost;

import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {

    if(prefix.equals(""))
    {
        Random random=new Random();
        int r=random.nextInt(words.size());
        return words.get(r);
    }
        else
    {
        int l=0,h=words.size()-1,m;
    while(l<=h)
    {
    m=(l+h)/2;
    if(words.get(m).startsWith(prefix))
    {
        return words.get(m);
    }
    else if(words.get(m).compareTo(prefix)>0)
    {
        h=m-1;
    }
    else
        l=m+1;
    }
    }

        return null;
    }

    @Override
    public String getGoodWordStartingWith(String prefix) {
        String selected = null;
        String st[]=new String[10000];
        int i=0;
        ArrayList<String> odd=new ArrayList<>();
        ArrayList<String> even =new ArrayList<>();
        if(prefix.equals(""))
        {
            Random random=new Random();
            int r=random.nextInt(words.size());
            return words.get(r);
        }

        int l=0,h=words.size()-1,m;
        while(l<=h)
        {
            m=(l+h)/2;
            if((words.get(m).startsWith(prefix)) && (i<10000))
            {
                st[i++]=words.get(m);
            }
            else if(words.get(m).compareTo(prefix)>0)
            {
                h=m-1;
            }
            else
                l=m+1;

        }

       for(int a=0;a<i;a++) {
           String s=st[a];
           if ((s.length() - prefix.length()) % 2 == 0) {
               even.add(s);
           } else
               odd.add(s);

       }
               if(even.size()!=0)
                   selected=even.get(new Random().nextInt(even.size()));
                else if(odd.size()!=0)
                    selected=odd.get(new Random().nextInt(odd.size()));
        return selected;
    }
}
