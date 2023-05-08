package me.lucthesloth.ssc.utils;

public class Utils {
    public static class KeyPair {
        public int x;
        public int z;
        public KeyPair(int x, int z) {
            this.x = x;
            this.z = z;
        }
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof KeyPair other) {
                return other.x == x && other.z == z;
            }
            return false;
        }
    }
}
