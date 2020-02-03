import java.util.Arrays;

public class FindSpyLowestCost implements IFindSpies {

    @Override
    public int[] findSpies(int N, int k, IMissionControl missionControl) {
        int spiesFound = 0, bitmap[] = new int[N];
        Arrays.fill(bitmap, 1);
        for(int i = 0; i<N; i++){
            if(N-i<=k-spiesFound){
                break;
            }
            bitmap[i]=0;
            if(spiesFound>=k){
                continue;
            }
            if(missionControl.sendForMission(bitmap)){
                //not spy
            } else{
                //i is a spy
                spiesFound++;
                bitmap[i]=1;
                continue;
            }
        }
        return bitmap;
    }
}
