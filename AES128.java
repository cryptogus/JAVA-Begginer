
/**
 * 옳은 값: 0x39, 0x25, 0x84, 0x1d, 0x02, 0xdc, 0x09, 0xfb, 0xdc, 0x11, 0x85, 0x97, 0x19, 0x6a, 0x0b, 0x32
 * @param args
 */
public class AES128 {

    static int mix2(int b) {
        int x;
        if (b >> 7 == 0) {
            x = (b << 1) & 0xff;
        }
        else {
            x = ((b << 1) ^ 0b00011011)& 0xff;
        }
        return x;
    }
    static int mix3(int a) {
        int x;
        if (a >> 7 == 1) {
            x = (((a << 1) ^ 0b00011011)& 0xff) ^ a;
        }
        else {
            x = ((a << 1)& 0xff) ^ a;
        }
        return x;
    }
    public static void main(String[] args){
        //test vector
        //byte 형은 1바이트 정수 자료형이지만 부호를 갖고 c언어 처럼 unsigned 를 붙일수가 없음. 따라서 4바이트 정수 자료형 int or 2바이트 정수 자료형 short를 사용
        
        int[] plainText = { 0x32, 0x43, 0xf6, 0xa8, 0x88, 0x5a, 0x30, 0x8d, 0x31, 0x31, 0x98, 0xa2, 0xe0, 0x37, 0x07, 0x34 };
	    int[] roundKey = { 0x2b, 0x7e, 0x15, 0x16, 0x28, 0xae, 0xd2, 0xa6, 0xab, 0xf7, 0x15, 0x88, 0x09, 0xcf, 0x4f, 0x3c };  /*마스터키*/
        final int[] roundConstant = { 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36 };

        //int[] standardCipherText = { 0x39, 0x25, 0x84, 0x1d, 0x02, 0xdc, 0x09, 0xfb, 0xdc, 0x11, 0x85, 0x97, 0x19, 0x6a, 0x0b, 0x32 };
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        int x = 0, y = 0, z = 0 ;

        int []state = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        int []userCipherText = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        
        int[] SBox = {
            0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76,
            0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0,
            0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15,
            0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75,
            0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84,
            0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf,
            0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8,
            0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2,
            0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73,
            0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb,
            0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79,
            0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08,
            0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a,
            0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e,
            0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf,
            0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16
        };

        for (int i = 0; i < 16; i++)
	    {
		    plainText[i] = plainText[i] ^ roundKey[i];	
	    }

        for (int k = 0; k < 9; k++) {
		
            /*Keyschedule*/
    
    
            roundKey[0] = roundKey[0] ^ roundConstant[k] ^ SBox[roundKey[13]];//G func
            roundKey[1] = roundKey[1] ^ SBox[roundKey[14]];
            roundKey[2] = roundKey[2] ^ SBox[roundKey[15]];
            roundKey[3] = roundKey[3] ^ SBox[roundKey[12]];
            
            for (int i = 0; i < 12; i++)
                roundKey[i + 4] = roundKey[i] ^ roundKey[i + 4];
            /*SubBytes*/
    
            for (int i = 0; i < 16; i++){
                plainText[i] = SBox[plainText[i]];
            }
    
            /*ShiftRows*/
    
            x = plainText[1];
            plainText[1] = plainText[5];
            plainText[5] = plainText[9];
            plainText[9] = plainText[13];
            plainText[13] = x;
            x = plainText[2]; y = plainText[6];
            plainText[2] = plainText[10];
            plainText[6] = plainText[14];
            plainText[10] = x;
            plainText[14] = y;
    
            x = plainText[3]; y = plainText[7]; z = plainText[11];
            plainText[3] = plainText[15];
            plainText[7] = x;
            plainText[11] = y;
            plainText[15] = z;
    
            /*Mixcoulumns*/
            //MixCoumns에서 state이용을 위함
            for (int a = 0; a < 16; a++){
                state[a] = plainText[a];
            }
    
            plainText[0] = mix2(state[0]) ^ mix3(state[1]) ^ state[2] ^ state[3];
            plainText[4] = mix2(state[4]) ^ mix3(state[5]) ^ state[6] ^ state[7];
            plainText[8] = mix2(state[8]) ^ mix3(state[9]) ^ state[10] ^ state[11];
            plainText[12] = mix2(state[12]) ^ mix3(state[13]) ^ state[14] ^ state[15];
            plainText[1] = state[0] ^ mix2(state[1]) ^ mix3(state[2]) ^ state[3];
            plainText[5] = state[4] ^ mix2(state[5]) ^ mix3(state[6]) ^ state[7];
            plainText[9] = state[8] ^ mix2(state[9]) ^ mix3(state[10]) ^ state[11];
            plainText[13] = state[12] ^ mix2(state[13]) ^ mix3(state[14]) ^ state[15];
            plainText[2] = state[0] ^ state[1] ^ mix2(state[2]) ^ mix3(state[3]);
            plainText[6] = state[4] ^ state[5] ^ mix2(state[6]) ^ mix3(state[7]);
            plainText[10] = state[8] ^ state[9] ^ mix2(state[10]) ^ mix3(state[11]);
            plainText[14] = state[12] ^ state[13] ^ mix2(state[14]) ^ mix3(state[15]);
            plainText[3] = mix3(state[0]) ^ state[1] ^ state[2] ^ mix2(state[3]);
            plainText[7] = mix3(state[4]) ^ state[5] ^ state[6] ^ mix2(state[7]);
            plainText[11] = mix3(state[8]) ^ state[9] ^ state[10] ^ mix2(state[11]);
            plainText[15] = mix3(state[12]) ^ state[13] ^ state[14] ^ mix2(state[15]);
    
            /*AddRound*/
            for (int i = 0; i < 16; i++)
            {   
                plainText[i] = plainText[i] ^ roundKey[i];
            }
    
        }
        
        /*10Round*/
        roundKey[0] = roundKey[0] ^ roundConstant[9] ^ SBox[roundKey[13]];
        roundKey[1] = roundKey[1] ^ SBox[roundKey[14]];
        roundKey[2] = roundKey[2] ^ SBox[roundKey[15]];
        roundKey[3] = roundKey[3] ^ SBox[roundKey[12]];
    
        for (int i = 0; i < 12; i++)//word 40~43
            roundKey[i + 4] = roundKey[i] ^ roundKey[i + 4];
    
        for (int i = 0; i < 16; i++)
            plainText[i] = SBox[plainText[i]];
    
        //ShiftRows
        x = plainText[1];
        plainText[1] = plainText[5];
        plainText[5] = plainText[9];
        plainText[9] = plainText[13];
        plainText[13] = x;
        x = plainText[2]; y = plainText[6];
        plainText[2] = plainText[10];
        plainText[6] = plainText[14];
        plainText[10] = x;
        plainText[14] = y;
    
        x = plainText[3]; y = plainText[7]; z = plainText[11];
        plainText[3] = plainText[15];
        plainText[7] = x;
        plainText[11] = y;
        plainText[15] = z;
        System.out.println("암호문");
        //AddRound
        for (int i = 0; i < 16; i++)
        {
            userCipherText[i] = plainText[i] ^ roundKey[i];
            System.out.printf("%02x ", userCipherText[i]);
            //옳은 값: 0x39, 0x25, 0x84, 0x1d, 0x02, 0xdc, 0x09, 0xfb, 0xdc, 0x11, 0x85, 0x97, 0x19, 0x6a, 0x0b, 0x32
        }
    
    }

}
