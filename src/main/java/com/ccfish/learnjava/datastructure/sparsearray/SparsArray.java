package com.ccfish.learnjava.datastructure.sparsearray;

import java.util.ArrayList;
import java.util.List;

/**
 * 稀疏数组
 * @Author: Ciaos
 * @Date: 2020/3/9 20:54
 */

public class SparsArray {

    // 五子棋，1表示黑子2表示白子
    public static void main(String[] args) {
        // 创建原始二维数组
        int chessArray1[][] = new int[11][11];
        chessArray1[1][3] = 1;
        chessArray1[2][4] = 2;
        chessArray1[5][6] = 1;
        System.out.println("原始的二维数组");
        for (int[] row : chessArray1){
            for (int value : row){
                System.out.printf("%d \t",value);
            }
            System.out.println();
        }

        // 转换成稀疏数组
        // 1. 遍历二维数组
        int sum = 0;
        for (int i = 0;i< 11;i++){
            for (int j =0;j<11;j++){
                if (chessArray1[i][j] != 0 ){
                    sum++;
                }
            }
        }
        // 2. 第一行赋值 几行几列几个值
        int sparseArray[][] = new int[sum+1][3];
        sparseArray[0][0] = 11;
        sparseArray[0][1] = 11;
        sparseArray[0][2] = sum;
        // 3. 遍历二维数组 将非零的值存放到稀疏数组中
        int count = 0; // 用于记录时第几个非零数据
        for (int i = 0;i< 11;i++){
            for (int j =0;j<11;j++){
                if (chessArray1[i][j] != 0 ){
                    count++;
                    sparseArray[count][0] = i;
                    sparseArray[count][1] = j;
                    sparseArray[count][2] = chessArray1[i][j];
                }
            }
        }
        // 4. 输出
        System.out.println();
        System.out.println("得到的稀疏数组为如下形式");
        for (int i =0;i<sparseArray.length; i++){
            System.out.printf("%d \t %d \t %d\t", sparseArray[i][0], sparseArray[i][1], sparseArray[i][2]);
            System.out.println();
        }


        // 将稀疏数组恢复成原始的二维数组
        // 1. 创建二位数组
        int length = sparseArray[0][0];
        int height = sparseArray[0][1];
        int chessArray2[][] = new int[length][height];
        // 2.遍历稀疏数组 给新的二维数组赋值
        for (int i=1;i<sparseArray.length;i++){
            // 赋值
            int row = sparseArray[i][0];
            int col = sparseArray[i][1];
            int value = sparseArray[i][2];
            chessArray2[row][col] = value;
        }
        // 3. 输出新的二维数组
        System.out.println();
        System.out.println("转换回的二维数组");
        for (int[] row : chessArray2){
            for (int value : row){
                System.out.printf("%d \t",value);
            }
            System.out.println();
        }
    }
}
