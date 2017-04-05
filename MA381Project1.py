import random
import time
import math

"""
<describe what this module has/does>

Created on Oct 2, 2016.
Written by: nurrencd.
"""


def main():
    """ Calls the   TEST   functions in this module. """
    n = 10000
    st2 = SpinTracker()  # data object
    st3 = SpinTracker()
    st4 = SpinTracker()
    for k in range(20):  # for each limit, 1-20
        for j in range(n):  # 10,000 games played
            twoPlayerGame(st2, k + 1)
            threePlayerGame(st3, k + 1)
            fourPlayerGame(st4, k + 1)
    printResults(st2, 2)
    printResults(st3, 3)
    printResults(st4, 4)



def twoPlayerGame(st, limit):
    # get first spin
    spin1 = spin(limit)
    st.spinCount += 1  # record in data...
    st.spinCountArray[limit - 1][spin1 - 1] += 1
    if (spin1 == -1):  # record if goes over...
        st.overCount += 1
        st.overCountArray[limit - 1] += 1
    else:
        # p2's limit is p1's spin total
        spin2 = spin(spin1 + 1)
        if (spin1 >= spin2):
            # p1 wins
            st.winCount += 1
            st.winCountArray[limit - 1][spin1 - 1] += 1
    return

def threePlayerGame(st, limit):
    # get first spin
    spin1 = spin(limit)
    st.spinCount += 1  # record in data...
    st.spinCountArray[limit - 1][spin1 - 1] += 1
    if (spin1 == -1):  # record if goes over...
        st.overCount += 1
        st.overCountArray[limit - 1] += 1
    else:
        # p2's limit is p1's spin total, or optimized
        spin2 = spin(max(min(spin1 + 1, 20), 12))
        spin3 = spin(max(min(spin2 + 1, 20), min(spin1 + 1, 20)))
        if (spin1 >= spin2 and spin1 >= spin3):
            # p1 wins
            st.winCount += 1
            st.winCountArray[limit - 1][spin1 - 1] += 1
    return

def fourPlayerGame(st, limit):
    # get first spin
    spin1 = spin(limit)
    st.spinCount += 1  # record in data...
    st.spinCountArray[limit - 1][spin1 - 1] += 1
    if (spin1 == -1):  # record if goes over...
        st.overCount += 1
        st.overCountArray[limit - 1] += 1
    else:
        # p2's limit is either p1's spin +1 or 14, as projected by experiments
        spin2 = spin(max(min(spin1 + 1, 20), 14))
        # p3's limit is either the highest spin +1 or 11
        spin3 = spin(max(min(spin2 + 1, 20), min(spin1 + 1, 20), 12))
        # p4's limit is the highest spin
        spin4 = spin(max(min(spin2 + 1, 20), min(spin3 + 1, 20), min(spin1 + 1, 20)))
        if (spin1 >= spin2 and spin1 >= spin3 and spin1 >= spin4):  # p1 wins
            st.winCount += 1
            st.winCountArray[limit - 1][spin1 - 1] += 1
    return


def spin(limit):
    # get first spin
    spin = math.ceil(random.random() * 20)
    # spin if not desired goal
    if (spin < limit):
        # second spin...
        spin += math.ceil(random.random() * 20)
        if (spin > 20):
            # went over 20...
            return -1
    return spin



def printResults(st, num):
    print("In a game with " + str(num) + " players: ")
    for k in range(20):
#         print(" - If your limit was " + str(k + 1) + ":")
        spinCount = 0
        winCount = 0
        for j in range(20):
            spinCount += st.spinCountArray[k][j]
            winCount += st.winCountArray[k][j]
#             print(" --- Success rate with spin value " + str(j + 1) + ": " + str(st.winCountArray[k][j]) + " of " + str(st.spinCountArray[k][j]) + ".")
        print("Total wins with limit " + str(k + 1) + ": " + str(winCount) + " of " + str(spinCount + st.overCountArray[k]) + ".")
#         print("Total times going over 20: " + str(st.overCountArray[k]))
#         print("---------------------------------------------------------")
    print("Total wins: " + str(st.winCount) + " of " + str(st.spinCount))
    print("Total times going over 20: " + str(st.overCount))
    print("---------------------------------------------------------")
    print("---------------------------------------------------------")
    print("---------------------------------------------------------")

class SpinTracker():
    def __init__(self):
        self.overCount = 0
        self.spinCount = 0
        self.winCount = 0
        self.overCountArray = []
        self.spinCountArray = []
        self.winCountArray = []
        for k in range(20):
            array1 = []
            array2 = []
            for k in range(22):  # 22 instead of 20 to combat weird
                                    # negative indexing problem... oops
                array1.append(0)
                array2.append(0)
            self.winCountArray.append(array1)
            self.spinCountArray.append(array2)
            self.overCountArray.append(0)
            # sets up arrays of loops


#-----------------------------------------------------------------------
# If this module is running at the top level (as opposed to being
# imported by another module), then call the 'main' function.
#-----------------------------------------------------------------------
if __name__ == '__main__':
    main()
