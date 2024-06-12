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


expected_rover_camera_types = {
    MarsRover.CURIOSITY:   [CameraType.FHAZ, CameraType.RHAZ, CameraType.CHEMCAM, CameraType.MAHLI, CameraType.MARDI, CameraType.NAVCAM],
    MarsRover.OPPORTUNITY: [CameraType.FHAZ, CameraType.RHAZ, CameraType.NAVCAM, CameraType.PANCAM, CameraType.MINITES],
    MarsRover.SPIRIT:      [CameraType.FHAZ, CameraType.RHAZ, CameraType.NAVCAM, CameraType.PANCAM, CameraType.MINITES]
}
