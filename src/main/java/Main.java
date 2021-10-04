import java.math.BigInteger;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        char[][] triplets = {
                {'t','u','p'},
                {'w','h','i'},
                {'t','s','u'},
                {'a','t','s'},
                {'h','a','p'},
                {'t','i','s'},
                {'w','h','s'}
        };
        System.out.println(recoverSecret(triplets));
    }

    public static String recoverSecret(char[][] triplets) {
        StringBuilder builder = new StringBuilder();
        Set<Character> uniqueChars = new HashSet<>();
        for (int i = 0; i < 8; i++) {

        }
        for (char c : uniqueChars) {
            builder.append(c);
        }
        return builder.toString();
    }

    public static List<String> top3(String s) {
        String cleanString = s.replaceAll("[^a-zA-Z' åäö]", "").toLowerCase().trim();
        Map<String, Integer> stringMap = new HashMap<>();
        for (String ss : cleanString.split(" ")) {
            if (ss.isEmpty() || ss.trim().matches("'+")) continue;
            stringMap.put(ss.trim(), stringMap.get(ss) == null ? 0 : stringMap.get(ss) + 1);
        }
        List<Map.Entry<String, Integer>> stringList = new ArrayList<>(stringMap.entrySet());
        stringList.sort((o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));
        List<String> resultList = new ArrayList<>();
        stringList.forEach(entry -> resultList.add(entry.getKey()));
        if (resultList.size() < 3) return resultList;
        return resultList.subList(0, 3);
    }

    public static int determinant(int[][] matrix) {
        if (matrix[0].length == 1) return matrix[0][0];
        if (matrix[0].length == 2) return matrix[0][0]*matrix[1][1] - matrix[0][1]*matrix[1][0];
        if (matrix[0].length == 3) return det3x3(matrix);

        int n = matrix[0].length;
        int det = 0;

        for (int i = 0; i < n; i++) {
            int[][] newMatrix = removeRowAndColumn(matrix, i);
            if (i % 2 == 0) det += det3x3(newMatrix);
            else det -= det3x3(newMatrix);
        }
        return det;
    }

    public static int[][] removeRowAndColumn(int[][] matrix, int column) {
        int[][] newMatrix = new int[matrix.length - 1][matrix.length - 1];
        int columnCounter = 0;
        for (int i = 1; i < newMatrix[0].length; i++) {
            for (int j = 0; j < newMatrix[0].length; j++) {
                if (j != column) {
                    newMatrix[i - 1][j] = matrix[i][columnCounter];
                }
                columnCounter++;
            }
        }
        return newMatrix;
    }

    public static int det3x3(int[][] matrix) {
        return (matrix[0][0] * ((matrix[1][1] * matrix[2][2]) - (matrix[1][2] * matrix[2][1])))
                - (matrix[0][1] * ((matrix[1][0] * matrix[2][2]) - (matrix[1][2] * matrix[2][0])))
                + (matrix[0][2] * ((matrix[1][0] * matrix[2][1]) - (matrix[1][1] * matrix[2][0])));
    }

    public static BigInteger perimeter(BigInteger n) {
        BigInteger[] fibSeq = fibonacci(n.intValue() + 2);
        BigInteger per = BigInteger.ZERO;
        for (BigInteger square :
                fibSeq) {
            per = per.add(square.multiply(BigInteger.valueOf(4)));
        }
        return per;
    }

    public static BigInteger[] fibonacci(Integer n) {
        BigInteger[] sequence = new BigInteger[n];
        for (int i = 0; i < n; i++) {
            if (i == 0) sequence[i] = BigInteger.valueOf(0);
            else if (i == 1) sequence[i] = BigInteger.valueOf(1);
            else sequence[i] = sequence[i - 1].add(sequence[i - 2]);
        }
        return sequence;
    }

    public static String makeReadable(int seconds) {
        int s = 0, m = 0, h = 0;
        int restS = seconds;

        while (restS > 0) {

            if (restS >= 3600) {
                h++;
                restS -= 3600;
            } else if (restS >= 60) {
                m++;
                restS -= 60;
            } else {
                s = restS;
                restS = 0;
            }
        }
        StringBuilder time = new StringBuilder();
        time.append(h >= 0 && h < 10 ? "0" + h : h)
                .append(":")
                .append(m >= 0 && m < 10 ? "0" + m : m)
                .append(":")
                .append(s >= 0 && s < 10 ? "0" + s : s);
        return time.toString();
    }

    public static int[] snail(int[][] array) {

        if (Arrays.stream(array).findAny().isEmpty()) return new int[0];
        int n = array.length;
        int iMax = n - 1, jMax = n - 1;
        int iMin = 0, jMin = 0;
        int i, j;
        int[] returnArray = new int[n * n];
        int counter = 0;

        String direction = "right";

        boolean done = false;
        while (!done) {

            switch (direction) {
                case "right":
                    i = iMin;
                    j = jMin;
                    for (; j <= jMax; j++) {
                        returnArray[counter] = array[i][j];
                        counter++;
                    }
                    direction = "down";
                    iMin++;
                    break;
                case "left":
                    i = iMax;
                    j = jMax;
                    for (; j >= jMin; j--) {
                        returnArray[counter] = array[i][j];
                        counter++;
                    }
                    direction = "up";
                    iMax--;
                    break;
                case "down":
                    i = iMin;
                    j = jMax;
                    for (; i <= iMax; i++) {
                        returnArray[counter] = array[i][j];
                        counter++;
                    }
                    direction = "left";
                    jMax--;
                    break;
                case "up":
                    i = iMax;
                    j = jMin;
                    for (; i >= iMin; i--) {
                        returnArray[counter] = array[i][j];
                        counter++;
                    }
                    direction = "right";
                    jMin++;
                    break;
            }
            if (iMin > iMax || jMin > jMax) done = true;
        }

        return returnArray;
    }

    public static String rangeExtraction(int[] arr) {
        StringBuilder returnString = new StringBuilder();
        for (int i = 0; i < arr.length; ) {
            int range = 0;
            for (int j = i; j <= i + range; j++) {
                if (j >= arr.length - 1) break;
                if (arr[j] + 1 == arr[j + 1]) {
                    range++;
                }
            }
            if (i == 0) returnString.append(range >= 2 ? arr[i] + "-" + arr[i + range] : arr[i]);
            else returnString.append(",").append(range >= 2 ? arr[i] + "-" + arr[i + range] : arr[i]);
            if (range < 2) {
                i++;
            } else {
                i += range + 1;
            }
        }
        return returnString.toString();
    }

    public static String tickets(int[] peopleInLine) {
        int twentyfives = 0;
        int fifties = 0;
        for (int bill : peopleInLine) {
            if (bill == 25) {
                twentyfives++;
            } else if (bill == 50 && twentyfives >= 1) {
                fifties++;
                twentyfives--;
            } else if ((bill == 100) && (fifties >= 1 && twentyfives >= 1)) {
                fifties--;
                twentyfives--;
            } else if (bill == 100 && twentyfives >= 3) {
                twentyfives -= 3;
            } else {
                return "NO";
            }

        }
        return "YES";
    }

    public static int sequence(int[] arr) {
        if (arr.length == 0 || Arrays.stream(arr).allMatch(x -> x < 0)) return 0;
        int sequenceSize = 1;
        int current;
        int max = Arrays.stream(arr).sum();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j <= arr.length - sequenceSize; j++) {
                current = Arrays.stream(Arrays.copyOfRange(arr, j, j + sequenceSize)).sum();
                if (current > max) max = current;
            }
            sequenceSize++;
        }
        return max;
    }

    public static int countBits(int n) {
        return Integer.bitCount(n);
    }

    public boolean check(String sentence) {
        Set<Character> chars = new HashSet<>();
        for (char c : sentence.toLowerCase().toCharArray()) {
            if (Character.isLetter(c)) chars.add(c);
        }
        return chars.size() == 26;
    }

    public static int findEvenIndex(int[] arr) {
        int sumLeft = 0;
        int sumRight = 0;
        for (int i = 0; i < arr.length; i++) {
            if (i == 0) {
                sumRight = Arrays.stream(arr).sum() - arr[i] - sumLeft;
            } else {
                sumLeft += arr[i - 1];
                sumRight -= arr[i];
            }
            if (sumLeft == sumRight) return i;
        }
        return -1;
    }

    public static int countPassengers(ArrayList<int[]> stops) {
        int peopleOnTheBus = 0;
        for (int[] ia : stops) peopleOnTheBus += ia[0] - ia[1];
        return stops.stream()
                .mapToInt(x -> x[0] - x[1])
                .sum();
    }

    public static int duplicateCount(String text) {
        Map<Character, Integer> uniqueChars = new HashMap<>();
        int counter;
        for (Character c : text.toLowerCase().toCharArray()) {
            counter = uniqueChars.get(c) == null ? 0 : uniqueChars.get(c);
            uniqueChars.put(c, counter + 1);
        }
        int returnValue = 0;
        for (Integer i : uniqueChars.values()) {
            if (i > 1) {
                returnValue++;
            }
        }
        return returnValue;
    }

    public static int[] arrayDiff(int[] a, int[] b) {
        List<Integer> diffArray = new ArrayList<>();
        boolean toAdd;
        for (int ia : a) {
            toAdd = true;
            for (int ib : b) {
                if (ia == ib) {
                    toAdd = false;
                    break;
                }
            }
            if (toAdd) diffArray.add(ia);
        }
        int[] returnArray = new int[diffArray.size()];
        for (int i = 0; i < diffArray.size(); i++) {
            returnArray[i] = diffArray.get(i);
        }
        return returnArray;
    }

    public static boolean comp(int[] a, int[] b) {
        int counter = 0;
        for (int ia : a) {
            for (int ib : b) {
                if (Math.sqrt(ib) == ia) {
                    counter++;
                    break;
                }
            }
        }
        return counter == a.length;
    }

    public static boolean isTriangle(int a, int b, int c) {
        List<Integer> sides = new ArrayList<>();
        sides.add(a);
        sides.add(b);
        sides.add(c);
        sides.forEach(System.out::println);
        Collections.sort(sides);
        sides.forEach(System.out::println);
        return sides.get(0) > sides.get(1) + sides.get(2);
    }

    public static int ConvertBinaryArrayToInt(List<Integer> binary) {
        int number = 0;
        int exponent = 0;
        for (int i = binary.size() - 1; i >= 0; i--) {
            if (binary.get(i) == 1) {
                number += Math.pow(2, exponent);
            }
            exponent++;
        }
        return number;
    }

    public static String spinWords(String sentence) {
        //TODO: Code stuff here
        String[] separateWords = sentence.split(" ");
        StringBuilder processedSentence = new StringBuilder();
        for (int i = 0; i < separateWords.length; i++) {
            if (separateWords[i].length() >= 5) {
                processedSentence.append(reverseWord(separateWords[i]));
            } else {
                processedSentence.append(separateWords[i]);
            }
            if (i < separateWords.length - 1) {
                processedSentence.append(" ");
            }
        }
        return processedSentence.toString();
    }


    public static boolean isSquare(int n) {
        double squareRoot = Math.sqrt(n);
        return squareRoot * squareRoot == n;// fix me!
    }

    public static String reverseWord(String word) {
        StringBuilder reversedWord = new StringBuilder();
        for (int i = word.length() - 1; i >= 0; i--) {
            reversedWord.append(word.charAt(i));
        }
        return reversedWord.toString();
    }

}
