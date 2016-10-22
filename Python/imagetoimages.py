import httplib, urllib, base64
import sys
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

def entityextraction(inputvalue):
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
        body="{\"language\" : \"en\",\"analyzerIds\" : [\"4fa79af1-f22c-408d-98bb-b7d7aeef7f04\"],\"text\" : \""+inputvalue+"\" }"
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
        grammar=["VB","RB","NN","NNP","VBG","NNS","DT","JJ"]
        count=0
        result=[]
        for i in ind:
            for x in grammar:
                if i==x:
                    result.append(actualstring[count])
            count=count+1
        #print result
        conn.close()
        return result
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
#url="https://s-media-cache-ak0.pinimg.com/236x/02/cd/79/02cd7964ae527af923fe9890de199740.jpg"
string,coordinates=imagetotext(url)
#print string
print coordinates
listvalues=entityextraction(string)
for i in listvalues:
    print genImageLink(i)   
