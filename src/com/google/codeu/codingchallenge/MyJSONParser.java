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
		System.out.println(in);
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
			System.out.println("Found semicolon" + skipSecondQuote.substring(0,1));
	
            // If there is a semicolon, there is a value 
            // associated with that key
            //find the value and put it into your object
            
            String skipSemi;
            //remove the semicolom from the stirng and remove whitespace
            skipSemi = skipSecondQuote.substring(1).trim();
            System.out.println("Hello"+skipSemi);
            if(skipSemi.substring(0,1).equals("\"")){
				System.out.println("Found a begining quote for a value");
                int tempStartVal;
                String newIn;
                int tempEndVal;
                String tempValue;

				//remove the opening brace and find the string
				
                skipFirstQuote = skipSemi.substring(1);
                tempEndVal = skipFirstQuote.indexOf("\"");
                System.out.println("Found an end quote for a value");
                tempValue = skipFirstQuote.substring(0, tempEndVal);
                in = skipFirstQuote.substring(tempEndVal+1).trim();
                tempValue = "\""+tempValue.trim()+"\"";  //remove white space in the value string

                //put into hashtable
                System.out.println(skipFirstQuote.substring(tempEndVal+1));
                allData.put(tempKey,tempValue);
            }
            else if(skipSemi.substring(0,1).equals("{")){
				System.out.println("Found an opening brace for a value");
                // If the symbol after the semicolon is a "{"
                // then there is another hashtable after the key
                // but we still add to the hashtable,
                // we just need to recurse on the hashtable
                String newIn;
                int endBraket;
                String tempValHT;

                newIn = skipFirstQuote.substring(endQuote+2);
                endBraket = newIn.indexOf("}");
                System.out.println("Found a closing brace for a value");
                tempValHT = newIn.substring(1,endBraket);
                //first we parse the hashtable so that
                //it passes the parsed hashtable in and 
                //not a string representing the JSON
                //then we put it in the object
                Hashtable parsedHT = new Hashtable();
                MyJSON tempMJ;
                System.out.println("We are going ro recurse");
                tempMJ = parse(tempValHT);
                System.out.println(tempMJ);
                System.out.println("We recursed");
                
                
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
			System.out.println("Found a comma");
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
                System.out.println("Found an opening quote for a key after a comma");
                skipFirstQuote = skipFirstQuote.substring(1);
                System.out.println(skipFirstQuote);
                System.out.println("What the fuck is happening");
                endQuote = skipFirstQuote.indexOf("\"");
                System.out.println("Found an closing quote for a key after a comma");
                tempKey = skipFirstQuote.substring(startQuote,endQuote);
                System.out.println(tempKey);
                tempKey = "\""+tempKey.trim()+"\"";
                
        
				String newStringIn;
				
				newStringIn = skipFirstQuote.substring(endQuote).trim();
				System.out.println(newStringIn);
                if(newStringIn.substring(0,1).equals(":")) {
					System.out.println("Found a semicolon after a key after a comma");
                    // If there is a semicolon, there is a value 
                    // associated with that key
                    //find the value and put it into the hashtable
                    
                    //get rid of whitespace between the semicolon and the next string
                    newStringIn = newStringIn.substring(1).trim();
                    if(newStringIn.substring(0,1).equals("\"")){
						System.out.println("Found an opening quote for a val after a commas");
                        String newIn;
                        int tempEndVal;
                        String tempValue;

						//find the next string
                        newIn = newStringIn.substring(endQuote+2);
                        tempEndVal = newIn.indexOf("\"");
                        System.out.println("Found a close quote for a val after a commas");
                        tempValue = newIn.substring(0,tempEndVal);
                        tempValue = "\""+tempValue.trim()+"\"";

                        //add to hashtable
                        allData.put(tempKey,tempValue);
                        
                        //update values to check the do while loop
                        nextCharacter = newIn.substring(tempEndVal,tempEndVal+1);
                        newStringIn = newIn.substring(tempEndVal);
                    }
                    else if(newStringIn.substring(0, 1).equals("{")){
						System.out.println("Found an opening brace after as a value");
                        // If the symbol after the semicolon is a "{"
                        // then there is another hashtable after the key
                        // but we still add to the hashtable
                        String newIn;
                        int endBraket;
                        String tempValHT;

                        newIn = newStringIn.substring(1);
                        endBraket = newIn.indexOf("}");
                        System.out.println("Found a closing brace as a value");
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
                //Need to update the next character here to check what it is

            } while (!nextCharacter.equals(""));
        } 
        else{
            //might want to continue the loop, maybe have a while loop
            //so if on eline is messed up but keep going??
            IOException e = new IOException();
            throw e;
        }   
    }
    MyJSON MJ = new MyJSON(allData);
    return MJ;
  }
}
