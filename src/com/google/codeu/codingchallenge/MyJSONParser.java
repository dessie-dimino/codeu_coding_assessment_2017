// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

//package com.google.codeu.codingchallenge;

import java.io.IOException;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Enumeration;

final class MyJSONParser implements JSONParser {

  @Override
  public MyJSON parse(String in) throws IOException {

    Hashtable allData = new Hashtable();
    //Will need to set things in this object

    //so instead of putting values into a hashtable,
    //use the functions you just wrote
    
    //removes whitespace at the beginning of the string
	in = in.trim();

    if(in.substring(0,1).equals("{")) {
		in = in.trim();
		//check for start of a hashtable, if so, recurse
		MyJSON MJ;
        MJ = parse(in.substring(1));   
        return MJ;
    } 
    else if(in.substring(0,1).equals("}")) {
		//check for the end of a hashtable, if so, return hashtable
		MyJSON tempMJ = new MyJSON(allData); 
        return tempMJ;
    }
    else if (in.substring(0,1).equals("\"")) {
		// if a string is found, the string will be a key (because its
		// coming right after a curly brace) so find the string and save it
        String skipFirstQuote;
        int endQuote;
        String tempKey;

        skipFirstQuote = in.substring(1);
        endQuote = skipFirstQuote.indexOf("\"");
		
        tempKey = skipFirstQuote.substring(0,endQuote);
        tempKey = "\"" + tempKey.trim() + "\"";  // get rid of any potential whitespace in the string
        
		// there should be a semicolon afterwards, if there isn't
		// there is an error
		//remove white space between where the string ended at where the 
		//semicolon should be
		String skipSecondQuote;
		skipSecondQuote = skipFirstQuote.substring(endQuote+1).trim();
		
        if (skipSecondQuote.substring(0,1).equals(":")) {
			
            // If there is a semicolon, there is a value 
            // associated with that key
            //find the value and put it into your object
            
            String skipSemi;
            //remove the semicolom from the stirng and remove whitespace
            skipSemi = skipSecondQuote.substring(1).trim();
            if(skipSemi.substring(0,1).equals("\"")){
                int tempStartVal;
                String newIn;
                int tempEndVal;
                String tempValue;

				//remove the opening brace and find the string
				
                skipFirstQuote = skipSemi.substring(1);
                tempEndVal = skipFirstQuote.indexOf("\"");
                tempValue = skipFirstQuote.substring(0, tempEndVal);
                in = skipFirstQuote.substring(tempEndVal+1).trim();
                tempValue = "\""+tempValue.trim()+"\"";  //remove white space in the value string

                //put into hashtable
                allData.put(tempKey,tempValue);
            }
            else if(skipSemi.substring(0,1).equals("{")){
				
                // If the symbol after the semicolon is a "{"
                // then there is another hashtable after the key
                // but we still add to the hashtable,
                // we just need to recurse on the hashtable
                String newIn;
                int endBraket;
                String tempValHT;

                newIn = skipFirstQuote.substring(endQuote+2);
                endBraket = newIn.indexOf("}");
                tempValHT = newIn.substring(1,endBraket);
                //first we parse the hashtable so that
                //it passes the parsed hashtable in and 
                //not a string representing the JSON
                //then we put it in the object
                Hashtable parsedHT = new Hashtable();
                MyJSON tempMJ;
                tempMJ = parse(tempValHT);
               
                
                
                in = newIn.substring(endBraket).trim();
                //After the nested hashtable, the rest of the code must be parsed
                //changing this will allow it to be picked up by the next if statement outside this one.
                
                allData.put(tempKey, tempMJ);
                
            }
            else if(in.substring(endQuote+1, endQuote+2).equals("}") || skipFirstQuote.substring(0,1).equals("}")){
					MyJSON MJ = new MyJSON(allData);
					return MJ;
				}
        }
        if(in.substring(0,1).equals(",")) {
            //keep finding pair values and putting them into the object
            //until you hit a curly brace
            //keep checking for strings 
            String nextCharacter;
            
            //Get rid of the comman and anyspaces
            skipFirstQuote = in.substring(1).trim();
				
				
            do{
                // CHECK IN HERE TO MAKE SURE THE VALUE OF NEXTCHARACTER
                // GETS UPDATED
                
				skipFirstQuote = in.trim();
                nextCharacter = skipFirstQuote.substring(0,1);
                int startQuote;
				skipFirstQuote = in.substring(1).trim();
				//find the next string, will be a key
                startQuote = skipFirstQuote.indexOf("\"");
                skipFirstQuote = skipFirstQuote.substring(1);
                endQuote = skipFirstQuote.indexOf("\"");
                tempKey = skipFirstQuote.substring(startQuote,endQuote);
                tempKey = "\""+tempKey.trim()+"\"";
                
        
				String newStringIn;
				
				newStringIn = skipFirstQuote.substring(endQuote).trim();
                if(newStringIn.substring(0,1).equals(":")) {
                    // If there is a semicolon, there is a value 
                    // associated with that key
                    //find the value and put it into the hashtable
                    
                    //get rid of whitespace between the semicolon and the next string
                    newStringIn = newStringIn.substring(1).trim();
                    if(newStringIn.substring(0,1).equals("\"")){
                        String newIn;
                        int tempEndVal;
                        String tempValue;

						//find the next string
                        newIn = newStringIn.substring(endQuote+2);
                        tempEndVal = newIn.indexOf("\"");
                        tempValue = newIn.substring(0,tempEndVal);
                        tempValue = "\""+tempValue.trim()+"\"";

                        //add to hashtable
                        allData.put(tempKey,tempValue);
                        
                        //update values to check the do while loop
                        nextCharacter = newIn.substring(tempEndVal,tempEndVal+1);
                        newStringIn = newIn.substring(tempEndVal);
                    }
                    else if(newStringIn.substring(0, 1).equals("{")){
                        // If the symbol after the semicolon is a "{"
                        // then there is another hashtable after the key
                        // but we still add to the hashtable
                        String newIn;
                        int endBraket;
                        String tempValHT;

                        newIn = newStringIn.substring(1);
                        endBraket = newIn.indexOf("}");
                        tempValHT = newIn.substring(0,endBraket);
                        tempValHT = tempValHT.trim();
                        
                        //check to see if the loop should keep going
                        nextCharacter = newIn.substring(endBraket,endBraket+1);
                        newStringIn = newIn.substring(endBraket);
						
                        MyJSON tempMJ;
                        tempMJ = parse(tempValHT);
                        allData.put(tempKey, tempMJ);
                    }
                }
                // Need to update the next character here to check what it is

            } while (!nextCharacter.equals(""));
        } 
        else{
            // might want to continue the loop, maybe have a while loop
            // so if on eline is messed up but keep going??
            IOException e = new IOException();
            throw e;
        }   
    }
    MyJSON MJ = new MyJSON(allData);
    return MJ;
  }
}

// Looking backat this now, I'm not entirely sure that I take care of all cases in my recursion.
// If I had time to start over, I would make one large do while loop that ran while the string still had characters
// I feel like that would more concisely take care of each possible case.
// I believe that my code should work for the empty brackets and for the single hashtable. 
// It was outputting the correct statements but it would not pass the given tests.  I believe this
// may have had to do with my MyJSON.java file.
// I believe it may also work for the nested hashtables.  However it cannot deal with commas
// and it's causing an infinite loop in my while statement which I cannot seem to fix. 

