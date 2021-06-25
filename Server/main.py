import numpy as np
import socket
import sqlite3
from scipy.spatial import distance




def Cook(CockData,CockName):
    ml = ML()
    ml.SetData(CockData, "InnerDatabase(SQLite).db")
    ml.testModel()
    res=ml.report(CockName)
    return res

class ML:
    def __init__(self):
        self._trainN = np.array([])  # train name
        self._trainId = np.array([]) # train id
        self._trainDa = np.array([])  # train sweet
        self._trainDb = np.array([])  # train alcohol
        self._trainDc = np.array([])  # train body
        self._trainDd = np.array([])  # train especial

        self._testN = np.array([])  # test name
        self._testDa = np.array([])  # test sweet
        self._testDb = np.array([])  # test alcohol
        self._testDc = np.array([])  # test body
        self._testDd = np.array([])  # test especial

        self._resArray = np.array([])
        self._k = 1  # k value for k-NN

    def SetData(self, CockData, fileName):  # set class train variables
        nArray, idArray, aArray, bArray, cArray, dArray = self.createTrainMatrices(fileName)
        self._trainN = nArray
        self._trainId = idArray
        self._trainDa = aArray
        self._trainDb = bArray
        self._trainDc = cArray
        self._trainDd = dArray

        aSet = [float(CockData[0])]
        bSet = [float(CockData[1])]
        cSet = [float(CockData[2])]
        dSet = [float(CockData[3])]

        self._testDa = np.array(aSet)
        self._testDb = np.array(bSet)
        self._testDc = np.array(cSet)
        self._testDd = np.array(dSet)

    def createTrainMatrices(self, fileName):  # Read data from Train db and make arrays
        nSet = []
        idSet = []
        aSet = []
        bSet = []
        cSet = []
        dSet = []
        con = sqlite3.connect(fileName)
        cur = con.cursor()
        cur.execute("SELECT * FROM cocktail_table")
        for row in cur:
            data = [x for x in row]
            n = data[0]
            id = data[6]
            a = float(data[1])
            b = float(data[2])
            c = float(data[3])
            d = float(data[4])

            nSet.append(n)
            idSet.append(id)
            aSet.append(a)
            bSet.append(b)
            cSet.append(c)
            dSet.append(d)

        nArray = np.array(nSet)
        idArray = np.array(idSet)
        aArray = np.array(aSet)
        bArray = np.array(bSet)
        cArray = np.array(cSet)
        dArray = np.array(dSet)
        return nArray, idArray, aArray, bArray, cArray, dArray

    def testModel(self):
        n = np.size(self._trainDa)
        resArray = []
        for i in range(0, n):
            res = self.kNN(i)
            resArray.append(res)
            self._resArray = resArray
        self._resArray = sorted(self._resArray)

    def kNN(self, i):
        n = self._trainN[i]
        id = self._trainId[i]
        res = distance.euclidean([self._testDa, self._testDb, self._testDc, self._testDd],
                                 [self._trainDa[i], self._trainDb[i], self._trainDc[i], self._trainDd[i]])
        return [res, n, id]

    def report(self,CockName):
        j=0
        i=0
        while i!=len(CockName):
            if CockName[i]==self._resArray[j][1]:
                i=0
                j=j+1
            else:
                i=i+1
        return self._resArray[j][2]

