import me.minidigger.voxelgameslib.api.block.Direction;
import me.minidigger.voxelgameslib.api.map.Vector3D;

/**
 * Created by Martin on 05.02.2017.
 */
public class Test {


    @org.junit.Test
    public void test() {
        Direction dir = Direction.NORTH_EAST;
        Vector3D orig = new Vector3D(0, 0, 0);
        Vector3D ahead = new Vector3D(orig.getX() + dir.getModX(), orig.getY() + dir.getModY(), orig.getZ() + dir.getModZ());

        Vector3D path = orig.add(ahead);
        Vector3D right = path.rotateAroundY(1.5708);// 90 degrees
        Vector3D left = right.multiply(-1);

        System.out.println(orig);
        System.out.println(ahead);
        System.out.println(path);
        System.out.println(right);
        System.out.println(left);
    }
}
