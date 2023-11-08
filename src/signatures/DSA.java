package signatures;

public class DSA {
    private Long privateKey;

    private Long[] publicKey;

    public DSA(){
        // empty
    }

    public Long[] sign(String text){
        return null;
    }

    public boolean verify(String text, Long[] publicKey, Long[] S){
        return true;
    }

    private Long generateK(){
        return null;
    }
}
