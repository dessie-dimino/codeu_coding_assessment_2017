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

import java.util.Collection;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;


final class MyJSON implements JSON {
	ArrayList valString;
	ArrayList valObjects;
	Hashtable allData;
	
  public MyJSON(){
    this.valString = new ArrayList();
    this.valObjects = new ArrayList();
    this.allData = new Hashtable();;
  }
	
  public MyJSON(Hashtable allData){
    this.valString = new ArrayList();
    this.valObjects = new ArrayList();
    this.allData = allData;
  }
 
  @Override
  public MyJSON getObject(String name) {
  // Get the value of the nested object with the given name. If there is
  // no nested object with that name, the method will return null
	if(this.allData.contains(name) && !String.class.isInstance(this.allData.get(name))){
		return (MyJSON)this.allData.get(name);
    }
    else{
		return null;
	}
  }

  @Override
  public JSON setObject(String name, JSON value) {
  //Set the value of the nested object with the given name. Any old value
  // should be overwritten. This method will always return a reference to
  // "this"
    this.allData.put(name,value);
    return this;
  }

  @Override
  public String getString(String name) {
  //Get the string value within this object that has the given name. if
  // there is no string with the given name, the method will return null.
   	if(this.allData.contains(name) && String.class.isInstance(allData.get(name))){
		return (String)this.allData.get(name);
    }
    else{
		return null;
	}
  }

  @Override
  public JSON setString(String name, String value) {
  // Set the string that should be stored under the given name. Any old value
  // should be overwritten. This method will always return a reference to
  // "this".
    this.allData.put(name,value);
    return this;
  }

  @Override
  public void getObjects(Collection<String> names) {
    // Copy the names of all object values to the given collection
       
    Iterator<Map.Entry> it;
	Map.Entry entry;

	it = this.allData.entrySet().iterator();
	while (it.hasNext()) {
		entry = it.next();
		if(!String.class.isInstance(allData.get(entry.getKey()))){
			names.add((String)entry.getKey());
		}
       }
    }

  @Override
  public void getStrings(Collection<String> names) {
    // Copy the names of all string values to the given collection.
    
    Iterator<Map.Entry> it;
	Map.Entry entry;

	it = this.allData.entrySet().iterator();
	while (it.hasNext()) {
		entry = it.next();
		if(String.class.isInstance(allData.get(entry.getKey()))){
			names.add((String)entry.getKey());
		}
       }
	}
}

