from enum import Enum


class CameraType(Enum):
    FHAZ = 'FHAZ'
    RHAZ = 'RHAZ'
    MAST = 'MAST'
    CHEMCAM = 'CHEMCAM'
    MAHLI = 'MAHLI'
    MARDI = 'MARDI'
    NAVCAM = 'NAVCAM'
    PANCAM = 'PANCAM'
    MINITES = 'MINITES'


class MarsRover(Enum):
    CURIOSITY = 'curiosity'
    OPPORTUNITY = 'opportunity'
    SPIRIT = 'spirit'


# Source: https://api.nasa.gov/?ref=public_apis
expected_rover_camera_types = {
    MarsRover.CURIOSITY:   [CameraType.FHAZ, CameraType.RHAZ, CameraType.MAST, CameraType.CHEMCAM, CameraType.MAHLI, CameraType.MARDI, CameraType.NAVCAM],
    MarsRover.OPPORTUNITY: [CameraType.FHAZ, CameraType.RHAZ, CameraType.NAVCAM, CameraType.PANCAM, CameraType.MINITES],
    MarsRover.SPIRIT:      [CameraType.FHAZ, CameraType.RHAZ,
                            CameraType.NAVCAM, CameraType.PANCAM, CameraType.MINITES]
}


def check_photo_cameras(element_id, element, rover_name):

    allowed_camera_types_for_rover = expected_rover_camera_types[MarsRover(
        rover_name)]

    for camera in element['cameras']:
        assert (CameraType(camera) in allowed_camera_types_for_rover), 'Reported photos in ' + element_id + ' with unexpected camera ' + \
            camera + ' for rover ' + rover_name + \
            '. Allowed cameras are ' + str(allowed_camera_types_for_rover)
