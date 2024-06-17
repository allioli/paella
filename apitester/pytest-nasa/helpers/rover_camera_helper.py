from enum import Enum


class CameraType(Enum):
    FHAZ = 'FHAZ' # Front Hazard Avoidance Camera
    RHAZ = 'RHAZ' # Rear Hazard Avoidance Camera
    MAST = 'MAST'
    CHEMCAM = 'CHEMCAM' # Chemistry and Camera Complex
    MAHLI = 'MAHLI' # Mars Hand Lens Imager
    MARDI = 'MARDI' # Mars Descent Imager
    NAVCAM = 'NAVCAM'
    PANCAM = 'PANCAM'
    MINITES = 'MINITES' # Miniature Thermal Emission Spectrometer (Mini-TES)
    EDL_RUCAM = 'EDL_RUCAM' # "Rover Up-Look Camera"
    EDL_DDCAM = 'EDL_DDCAM' # Descent Stage Down-Look Camera
    EDL_PUCAM1 = 'EDL_PUCAM1' # Parachute Up-Look Camera A
    EDL_PUCAM2 = 'EDL_PUCAM2' # Parachute Up-Look Camera B
    EDL_RDCAM = 'EDL_RDCAM'  # Rover Down-Look Camera
    FRONT_HAZCAM_LEFT_A = 'FRONT_HAZCAM_LEFT_A' # Front Hazard Avoidance Camera - Left
    FRONT_HAZCAM_RIGHT_A = 'FRONT_HAZCAM_RIGHT_A' # "Front Hazard Avoidance Camera - Right
    MCZ_LEFT = 'MCZ_LEFT'  # Mast Camera Zoom - Left
    MCZ_RIGHT = 'MCZ_RIGHT'  # Mast Camera Zoom - Right
    NAVCAM_LEFT = 'NAVCAM_LEFT'  # Navigation Camera - Left
    NAVCAM_RIGHT = 'NAVCAM_RIGHT'  # Navigation Camera - Right
    REAR_HAZCAM_LEFT = 'REAR_HAZCAM_LEFT'  # Rear Hazard Avoidance Camera - Left
    REAR_HAZCAM_RIGHT = 'REAR_HAZCAM_RIGHT' # Rear Hazard Avoidance Camera - Right
    SKYCAM = 'SKYCAM'  # MEDA Skycam
    SHERLOC_WATSON = 'SHERLOC_WATSON'
    SUPERCAM_RMI = 'SUPERCAM_RMI'  # SuperCam Remote Micro Imager
    LCAM = 'LCAM'  # Lander Vision System Camera


class MarsRover(Enum):
    PERSEVERANCE = 'perseverance'
    CURIOSITY = 'curiosity'
    OPPORTUNITY = 'opportunity'
    SPIRIT = 'spirit'


# Source: https://api.nasa.gov/?ref=public_apis
expected_rover_camera_types = {
    MarsRover.CURIOSITY:    [CameraType.FHAZ, CameraType.RHAZ, CameraType.MAST, CameraType.CHEMCAM, CameraType.MAHLI, CameraType.MARDI, CameraType.NAVCAM],
    MarsRover.OPPORTUNITY:  [CameraType.FHAZ, CameraType.RHAZ, CameraType.NAVCAM, CameraType.PANCAM, CameraType.MINITES],
    MarsRover.SPIRIT:       [CameraType.FHAZ, CameraType.RHAZ, CameraType.NAVCAM, CameraType.PANCAM, CameraType.MINITES],
    MarsRover.PERSEVERANCE: [CameraType.EDL_RUCAM, CameraType.EDL_DDCAM, CameraType.EDL_PUCAM1, CameraType.EDL_PUCAM2, CameraType.EDL_RDCAM, CameraType.FRONT_HAZCAM_LEFT_A,
                             CameraType.FRONT_HAZCAM_RIGHT_A, CameraType.MCZ_LEFT, CameraType.MCZ_RIGHT, CameraType.NAVCAM_LEFT, CameraType.NAVCAM_RIGHT,
                             CameraType.REAR_HAZCAM_LEFT, CameraType.REAR_HAZCAM_RIGHT, CameraType.SHERLOC_WATSON, CameraType.SKYCAM, CameraType.SUPERCAM_RMI,
                             CameraType.LCAM]
}


def check_photo_cameras(element_id, element, rover_name):

    for camera in element['cameras']:
        check_photo_camera(element_id=element_id, camera_name=camera, rover_name=rover_name)

def check_photo_camera(element_id, camera_name, rover_name):
    
    allowed_camera_types_for_rover = expected_rover_camera_types[MarsRover(
        rover_name)]
    
    assert (CameraType(camera_name) in allowed_camera_types_for_rover), 'Reported photos in ' + element_id + ' with unexpected camera ' + \
            camera_name + ' for rover ' + rover_name + \
            '. Allowed cameras are ' + str(allowed_camera_types_for_rover)