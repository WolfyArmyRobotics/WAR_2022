// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * Classe principal do robô
 */
public class Robot extends TimedRobot {
  private final Timer tempo = new Timer(); // Variável para definir o tempo
  private final VictorSP m_leftMotor1 = new VictorSP(0); // Variável que define a porta do motor esquerdo
  private final VictorSP m_rightMotor1 = new VictorSP(2); // Variável que define a porta do motor 
  private final DifferentialDrive m_robotDrive1 = new DifferentialDrive(m_leftMotor1, m_rightMotor1); // Variável que define o controle do motor
  private final XboxController m_driverController = new XboxController(0); // Variável dos botões do controle

  @Override
  public void robotInit() {
    m_robotDrive1.stopMotor();
  }

  @Override
  public void autonomousInit() {
    tempo.stop();
    tempo.reset();
    tempo.start();
  }
/**
 * Modo automatico do robo
 */
  @Override 
  public void autonomousPeriodic() {
    while (tempo.get() <= 2){
      m_robotDrive1.tankDrive(-0.5, -0.5);
    }
    m_robotDrive1.stopMotor();
    tempo.stop();
  }
  /**
   * Calcula velocidade do motor no eixo Y
   * 
   * @param yValue Valor da leitura do controle no eixo Y
   * @return Retorna valor calculado para Y. Reduzindo sua velocidade.
   */
  double frente(double yValue) {
    if (yValue > 0.5)
      return (0.5);
    else if (yValue < -0.5)
      return (-0.5);
    else
      return (yValue);
  }
  
  /**
   * Calcula velocidade do motor no eixo X
   * 
   * @param xValue Valor da leitura do controle no eixo X
   * @return Retorna valor calculado para X. Reduzindo sua velocidade.
   */
  double lados(double xValue) {
    if (xValue > 0.5)
     return (0.5);
    else if (xValue < -0.5)
     return (-0.5);
    else
     return (xValue);
  }

  @Override
  public void teleopPeriodic() {
    double yAxis = m_driverController.getY(Hand.kLeft);
    double xAxis = m_driverController.getX(Hand.kLeft);
    double ySpeed = frente(yAxis);
    double xSpeed = lados(xAxis);

    boolean button = m_driverController.getBumper(Hand.kRight);

    if (!button) {
      m_robotDrive1.tankDrive(ySpeed, ySpeed);
      if (xAxis != 0)
        m_robotDrive1.tankDrive(xSpeed, -xSpeed);
    }
    else {
      m_robotDrive1.tankDrive(yAxis, yAxis);
      if (xAxis != 0)
        m_robotDrive1.tankDrive(xSpeed, -xSpeed);
    }
  }
}
