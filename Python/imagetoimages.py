import httplib, urllib, base64
import sys

import websocket
import thread
import json
import requests
import urllib
import wave
import audioop
from time import sleep
import StringIO
import struct
import sys
import codecs
from xml.etree import ElementTree

def imagetotext(url):
    headers = {
        # Request headers
        'Content-Type': 'application/json',
        'Ocp-Apim-Subscription-Key': 'a81d6f58ee334c79b4b6956faeca20d3',
    }

    params = urllib.urlencode({
        # Request parameters
        'language': 'unk',
        'detectOrientation ': 'true',
    })

    try:
        conn = httplib.HTTPSConnection('api.projectoxford.ai')
        conn.request("POST", "/vision/v1.0/ocr?%s" % params, "{\"url\":\""+url+"\"}", headers)
        response = conn.getresponse()
        data = response.read()
        #print(data)
        word=""
        x=data.find("bounding")
        data=data[x+1:]
        data=data[data.find('"')+1:]
        data=data[data.find('"')+1:]
        #print data
        coordinates=data[:data.find('"')]
        #print coordinates
        while (data.find("text")!=-1):
            data=data[data.find("text")+len("text")+3:]
            #print data
            x=data.find('"')
            word=word+" "+str(data[:x]).lower()
        return word,coordinates
        conn.close()
    except Exception as e:
        print("[Errno {0}] {1}".format(e.errno, e.strerror))
def detectLanguage(text):
    headers = {
        # Request headers
        'Content-Type': 'application/json',
        'Ocp-Apim-Subscription-Key': '299921e134524fae91de7cfe72d5d370',
    }

    params = urllib.urlencode({
        # Request parameters
        'numberOfLanguagesToDetect': '1',
    })

    try:
        #text="Madame"
        body="{\"documents\": [{\"id\": \"string\",\"text\": \""+text+"\"} ]}"
        conn = httplib.HTTPSConnection('westus.api.cognitive.microsoft.com')
        conn.request("POST", "/text/analytics/v2.0/languages?%s" % params, body, headers)
        response = conn.getresponse()
        data = response.read()
        #print(data)
        x=data.find("iso6391Name\"")
        data= data[x+len("iso6391Name\""):]
        data=data[data.find('"')+1:]
        #print data
        x=data[:data.find('"')]
        conn.close()
        return x
    except Exception as e:
        print("[Errno {0}] {1}".format(e.errno, e.strerror))

def entityextraction(inputvalue,lang):
    headers = {
        # Request headers
        'Content-Type': 'application/json',
        'Ocp-Apim-Subscription-Key': 'afad113729f74919b1f2846a34d099da',
    }

    params = urllib.urlencode({
    })

    try:
        conn = httplib.HTTPSConnection('api.projectoxford.ai')
        #inputvalue="Please do not step on grass"
        body="{\"language\" : \""+lang+"\",\"analyzerIds\" : [\"4fa79af1-f22c-408d-98bb-b7d7aeef7f04\"],\"text\" : \""+inputvalue+"\" }"
        conn.request("POST", "/linguistics/v1.0/analyze?%s" % params, body, headers)
        response = conn.getresponse()
        data = response.read()
        value=data.find("result");
        value=data.find("result")+len("result")+4;
        data = data[value:]
        x=0
        ind=[]
        while (x!=-1):
            x=data.find('"')
            data=data[x+1:]
            sub= data[:data.find('"')]
            ind.append(sub)
            x=data.find('"')
            data=data[x+1:]
        del ind[-1]
        #print ind
        actualstring=inputvalue.split()
        #print actualstring
        grammar=["VB","RB","NN","NNP","VBG","NNS","DT","JJ","VBD"]
        count=0
        result=[]
        while count<len(actualstring):
            for x in grammar:
                if ind[count]==x:
                    result.append(actualstring[count])
            count=count+1
        #print "abc"
        return result
        #print result
        conn.close()
        
    except Exception as e:
        print("[Errno {0}] {1}".format(e.errno, e.strerror))
def genImageLink(inputValue):
    headers = {
        # Request headers
        'Ocp-Apim-Subscription-Key': '14f2ca99aac74c768b3166655becdc1f',
    }

    params = urllib.urlencode({
        # Request parameters
        'q': inputValue,
        'count': '10',
        'offset': '0',
        'mkt': 'en-us',
        'safeSearch': 'Moderate',
    })

    try:
        conn = httplib.HTTPSConnection('api.cognitive.microsoft.com')
        conn.request("GET", "/bing/v5.0/images/search?%s" % params, "{body}", headers)
        response = conn.getresponse()
        data = response.read()
        #print(data)
        x=data.find("contentUrl")
        data=data[x+1:]
        x=data.find('"')
        data=data[x+1:]
        #print data[0]
        x=data.find('"')
        #print data[x:]
        data=data[data.find('"')+1:]
        #print data
        sub=data[x-2:data.find('"')]
        #print sub
        conn.close()
        return sub
    except Exception as e:
        print("[Errno {0}] {1}".format(e.errno, e.strerror))
 
#inputvalue="Please do not step on grass"   
#inputvalue="No food or drinks"   
#inputvalue="No running in library"   
#inputvalue="No trespassing"   
#inputvalue="No smoking"   
#inputvalue="Wet floor ahead"   
#inputvalue="Slow down kids playing"   
#inputvalue="Do not enter workers only"  
url=sys.argv[1]
def GetToken(): #Get the access token from ADM, token is good for 10 minutes
    urlArgs = {
        'client_id': 'imagetranslationapi',
        'client_secret': 'WHbVzXbaQdWipvmfQ30rytDoP4TaK3PhC+ZiNWLr4WA=',
        'scope': 'http://api.microsofttranslator.com',
        'grant_type': 'client_credentials'
    }

    oauthUrl = 'https://datamarket.accesscontrol.windows.net/v2/OAuth2-13'

    try:
        oauthToken = json.loads(requests.post(oauthUrl, data = urllib.urlencode(urlArgs)).content) #make call to get ADM token and parse json
        finalToken = "Bearer " + oauthToken['access_token'] #prepare the token
    except OSError:
        pass

    return finalToken
#End GetToken

def GetTextAndTranslate(finalToken,fromLangCode,textToTranslate):

    toLangCode = "en"
    
    #Call to Microsoft Translator Service
    headers = {"Authorization ": finalToken}
    translateUrl = "http://api.microsofttranslator.com/v2/Http.svc/Translate?text={}&to={}".format(textToTranslate, toLangCode)
   
    try:
        translationData = requests.get(translateUrl, headers = headers) #make request
        translation = ElementTree.fromstring(translationData.text.encode('utf-8')) # parse xml return values
        return translation.text #display translation

    except OSError:
        pass

    print " "
 
#End GetTextAndTranslate()

#url="http://il3.picdn.net/shutterstock/videos/12627623/thumb/11.jpg"
url=sys.argv[1]
string,coordinates=imagetotext(url)
lang=detectLanguage(string)
#print string
print coordinates
#print string
token=GetToken()
string=GetTextAndTranslate(token,lang,string)
listvalues=entityextraction(string,"en")
for i in listvalues:
    print genImageLink(i)