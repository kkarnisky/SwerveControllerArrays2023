package frc.robot.subsystems;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.photonvision.PhotonCamera;
import org.photonvision.common.hardware.VisionLEDMode;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.Vision.PhotonVision.TargetThread1;
import frc.robot.commands.Vision.PhotonVision.TargetThread2;

public class VisionPoseEstimator extends SubsystemBase {

  public DriveSubsystem m_drive;

  public AprilTagFieldLayout m_fieldLayout;

  private Pose2d[] visionMeasurement1 = new Pose2d[2];

  private Pose2d[] visionMeasurement2 = new Pose2d[2];

  TargetThread1 tgtTh1;

  private String camera1Name = "cam-IP11";// 10.21.94.11

  public PhotonCamera m_cam;

  TargetThread2 tgtTh2;

  private String camera2Name = "cam-IP12";// 10.21.94.12

  public PhotonCamera m_cam2;

  int numCams = 2;

  private final int targetsInFle = 24;

  private boolean fieldFileRead;

  public HashMap<Integer, String> pipelines = new HashMap<Integer, String>();

  private Pose2d visionPoseEstimatedData;

  public VisionPoseEstimator(DriveSubsystem drive) {

    PhotonCamera.setVersionCheckEnabled(false);

    pipelines.put(0, "ReflectiveTape");
    pipelines.put(1, "AprilTag16h");
    pipelines.put(2, "Triangle");

    m_drive = drive;

    m_cam = new PhotonCamera(camera1Name);


    m_cam2.setLED(VisionLEDMode.kOff);

    String fieldFile = AprilTagFields.k2023ChargedUp.m_resourceFile;

    try {

      m_fieldLayout = new AprilTagFieldLayout(fieldFile);

    } catch (IOException e) {

      fieldFileRead = false;

      e.printStackTrace();

    }

    tgtTh1 = new TargetThread1(this, m_cam);

    
  }

  @Override
  public void periodic() {


  }

  public void setVisionPoseEsitmatedData(Pose2d pose) {

    visionPoseEstimatedData = pose;

  }

  public Pose2d getVisionPoseEstimatedData() {

    return visionPoseEstimatedData;
  }


}
